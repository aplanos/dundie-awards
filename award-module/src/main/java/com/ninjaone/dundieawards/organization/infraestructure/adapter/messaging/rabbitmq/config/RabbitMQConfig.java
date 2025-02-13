package com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.rabbitmq.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.*;

@Configuration
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    public RabbitMQConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Queue durableQueue() {
        return new Queue(properties.getQueueName(), true);
    }

    @Bean
    public DirectExchange durableExchange() {
        return new DirectExchange(
                properties.getExchangeName(),
                true, false
        );
    }

    @Bean
    public Binding binding(Queue durableQueue, DirectExchange durableExchange) {
        return BindingBuilder
                .bind(durableQueue)
                .to(durableExchange)
                .with(properties.getRoutingKey());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(properties.getExchangeName());
        rabbitTemplate.setRoutingKey(properties.getRoutingKey());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory singleThreadedListenerFactory(ConnectionFactory connectionFactory) {
        final var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        return factory;
    }
}
