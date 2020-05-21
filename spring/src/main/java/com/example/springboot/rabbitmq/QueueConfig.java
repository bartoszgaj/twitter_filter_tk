package com.example.springboot.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class QueueConfig {

    private AmqpAdmin amqpAdmin;

    public QueueConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue("queue_one", false));
    }
}
