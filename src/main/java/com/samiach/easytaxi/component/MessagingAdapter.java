package com.samiach.easytaxi.component;

import com.samiach.easytaxi.dto.event.EventDTO;
import com.samiach.easytaxi.utility.Constants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MessagingAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingAdapter.class);

    @RabbitListener(queues = Constants.RMQ_QUEUE_RAW_API_REQUEST)
    public void consumeFromRabbitMQAndPublishToKafka(EventDTO eventDTO) {
        LOGGER.info("EventQualifier::consumeFromRabbitMQAndPublishToKafka >> (QUALIFIED REQUEST) EVENT CONSUMED FROM QUEUE => {}", eventDTO);
        publishToKafka(eventDTO);
    }

    private void publishToKafka(EventDTO eventDTO) {
        System.out.println("Received Data : " + eventDTO);
        //kafkaTemplate.send("booking.request", eventDTO);
    }

    private void consumeFromKafkaAndPublishToRabbitMQ() {
    }

    private void publishToRabbitMQ(String message) {
    }
}
