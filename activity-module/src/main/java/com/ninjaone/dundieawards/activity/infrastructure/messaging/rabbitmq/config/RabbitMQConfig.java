package com.ninjaone.dundieawards.activity.infrastructure.messaging.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    public RabbitMQConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DirectExchange activityExchange() {
        return new DirectExchange(
                properties.getActivityExchangeName(),
                true, false
        );
    }

    @Bean
    public SimpleRabbitListenerContainerFactory singleThreadedListenerFactory(ConnectionFactory connectionFactory) {
        final var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        return factory;
    }

    @Bean
    @Qualifier("activityCreateQueue")
    public Queue activityCreateQueue() {
        return new Queue(properties.getActivityCreateQueueName(), true);
    }

    @Bean
    @Qualifier("activityCreateBinding")
    public Binding activityCreateBinding(@Qualifier("activityCreateQueue") Queue activityCreateQueue,
                                         DirectExchange activityExchange) {
        return BindingBuilder
                .bind(activityCreateQueue)
                .to(activityExchange)
                .with(properties.getActivityCreateRoutingKey());
    }

    @Bean
    @Qualifier("activityCreateRabbitTemplate")
    public RabbitTemplate activityCreateRabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(properties.getActivityExchangeName());
        rabbitTemplate.setRoutingKey(properties.getActivityCreateRoutingKey());
        return rabbitTemplate;
    }

    @Bean
    @Qualifier("activityRollbackQueue")
    public Queue activityRollbackQueue() {
        return QueueBuilder.durable(properties.getActivityRollbackQueueName())
                .deadLetterExchange("dlx.exchange") // Set DLX
                .deadLetterRoutingKey("dlq.routing.key") // Route to DLQ
                .build();
    }

    @Bean
    @Qualifier("activityRollbackBinding")
    public Binding activityRollbackBinding(@Qualifier("activityRollbackQueue") Queue activityRollbackQueue,
                                           DirectExchange activityExchange) {
        return BindingBuilder
                .bind(activityRollbackQueue)
                .to(activityExchange)
                .with(properties.getActivityRollbackRoutingKey());
    }

    @Bean
    @Qualifier("activityRollbackRabbitTemplate")
    public RabbitTemplate activityRollbackRabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(properties.getActivityExchangeName());
        rabbitTemplate.setRoutingKey(properties.getActivityRollbackRoutingKey());
        return rabbitTemplate;
    }

    @Bean
    @Qualifier("activityRollbackDeadLetterQueue")
    public Queue activityRollbackDeadLetterQueue() {
        return QueueBuilder.durable(String.format("%s_dlq", properties.getActivityRollbackQueueName()))
                .build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("dlx.exchange");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(activityRollbackDeadLetterQueue())
                .to(deadLetterExchange())
                .with("dlq.routing.key");
    }
}
