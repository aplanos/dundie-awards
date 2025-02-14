package com.ninjaone.dundieawards.activity.infraestructure.messaging.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
        return new Queue(properties.getActivityRollbackQueueName(), true);
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
}
