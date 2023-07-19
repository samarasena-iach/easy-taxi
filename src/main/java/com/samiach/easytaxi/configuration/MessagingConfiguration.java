package com.samiach.easytaxi.configuration;

import com.samiach.easytaxi.utility.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Constants.RMQ_EXCHANGE);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public Queue queueRawAPIRequest() {
        return new Queue(Constants.RMQ_QUEUE_RAW_API_REQUEST);
    }

    @Bean
    public Binding bindingRawAPIRequest(Queue queueRawAPIRequest, TopicExchange exchange) {
        return BindingBuilder.bind(queueRawAPIRequest).to(exchange).with(Constants.RMQ_RK_RAW_API_REQUEST);
    }
}
