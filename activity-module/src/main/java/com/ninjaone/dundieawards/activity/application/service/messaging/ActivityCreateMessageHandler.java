package com.ninjaone.dundieawards.activity.application.service.messaging;

import com.ninjaone.dundieawards.activity.application.dto.ActivityModel;
import com.ninjaone.dundieawards.activity.application.service.ActivityService;
import com.ninjaone.dundieawards.messaging.application.messaging.MessageHandler;
import com.ninjaone.dundieawards.messaging.application.messaging.publishers.ActivityEventPublisher;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEvent;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEventType;
import com.ninjaone.dundieawards.messaging.domain.event.activity_create.ActivityCreateEventV1;
import com.ninjaone.dundieawards.messaging.domain.event.activity_rollback.ActivityRollBackEventV1;
import com.ninjaone.dundieawards.messaging.domain.event.increase_dundie_awards.IncreaseDundieAwardsEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActivityCreateMessageHandler implements MessageHandler {

    @Value("${employee.pageSize:1}")
    private int pageSize;

    private final ActivityService activityService;
    private final ActivityEventPublisher activityEventPublisher;

    public ActivityCreateMessageHandler(ActivityService activityService,
                                        ActivityEventPublisher activityEventPublisher) {
        this.activityService = activityService;
        this.activityEventPublisher = activityEventPublisher;
    }

    @Override
    @Transactional
    public void handle(DomainEvent message) {
        log.info("Handling ACTIVITY_CREATE message: {}", message);

        if (message instanceof ActivityCreateEventV1 eventV1) {

            try {
                activityService.save(ActivityModel.createGiveOrganizationDundieAwards(
                        eventV1.id(),
                        eventV1.organizationId(),
                        Map.of(
                                "sender", eventV1.sender(),
                                "amount", String.valueOf(eventV1.amount())
                        )
                ));

                throw new RuntimeException();

            } catch (Exception e) {
                log.error("Creating activity failed. Initiating rollback process", e);

                activityEventPublisher.publishActivityRollBackEvent(
                        ActivityRollBackEventV1.create(
                                "activity-module", eventV1.organizationId(), eventV1.amount()
                        )
                );
            }
        } else {
            log.error("Activity Create Message version is not supported");
        }
    }

    @Override
    public DomainEventType getSupportedType() {
        return DomainEventType.ACTIVITY_CREATE;
    }
}
