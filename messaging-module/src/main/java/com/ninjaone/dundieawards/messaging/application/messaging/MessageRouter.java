package com.ninjaone.dundieawards.messaging.application.messaging;

import com.ninjaone.dundieawards.messaging.domain.event.DomainEvent;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MessageRouter {

    private final Map<DomainEventType, MessageHandler> handlerMap;

    public MessageRouter(List<MessageHandler> handlers) {
        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(MessageHandler::getSupportedType, Function.identity()));
    }

    public void route(DomainEvent message) {
        final var handler = handlerMap.get(message.type());
        if (handler != null) {
            handler.handle(message);
        } else {
            log.warn("No handler found for message type: {}", message.type());
        }
    }
}
