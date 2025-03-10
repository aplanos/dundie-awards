package com.ninjaone.dundieawards.organization.infrastructure.quartz;

import com.ninjaone.dundieawards.messaging.application.messaging.MessageRouter;
import com.ninjaone.dundieawards.organization.infrastructure.messaging.broker.MessageBroker;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DisallowConcurrentExecution
public class MessageBrokerSubscriptionJob implements Job {

    private final MessageBroker messageBroker;
    private final MessageRouter messageRouter;

    @Autowired
    public MessageBrokerSubscriptionJob(MessageBroker messageBroker,
                                        MessageRouter messageRouter) {
        this.messageBroker = messageBroker;
        this.messageRouter = messageRouter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            final var messages = messageBroker.getMessages();

            if (messages.isEmpty()) {
                log.info("No messages to process.");
                return;
            }

            log.info("Processing {} messages.", messages.size());
            messages.forEach(messageRouter::route);

        } catch (Exception e) {
            log.error("Error occurred during job execution.", e);
            throw new JobExecutionException(e);
        }
    }
}
