package com.ninjaone.dundieawards.organization.infraestructure.messaging.broker;

import com.ninjaone.dundieawards.messaging.domain.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
public class MessageBroker {
    private final BlockingQueue<DomainEvent> messagesQueue = new LinkedBlockingQueue<>();

    public MessageBroker() {
    }

    public void sendMessage(DomainEvent message) {
        try {
            messagesQueue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error sending message", e);
        }
    }

    public int getMessageCount() {
        return messagesQueue.size();
    }

    public List<DomainEvent> getMessages() {
        List<DomainEvent> messages = new ArrayList<>();
        messagesQueue.drainTo(messages);
        return messages;
    }
}
