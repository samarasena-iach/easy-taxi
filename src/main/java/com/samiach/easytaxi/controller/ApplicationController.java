package com.samiach.easytaxi.controller;

import com.samiach.easytaxi.dto.event.EventDTO;
import com.samiach.easytaxi.dto.event.EventPayloadDTO;
import com.samiach.easytaxi.dto.request.RideRequestDTO;
import com.samiach.easytaxi.utility.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
@Validated
public class ApplicationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping(path = "/ride/request", consumes = "application/json", produces = "application/json")
    public String requestRide(@RequestBody RideRequestDTO rideRequestDTO) {
        try {
            LOGGER.info("ApplicationController::requestRide >> INCOMING REQUEST => {}", rideRequestDTO);

            Map<String, Object> fieldMap = new HashMap<>();
            Field[] declaredFields = rideRequestDTO.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object value = field.get(rideRequestDTO);
                fieldMap.put(field.getName(), value);
            }
            EventPayloadDTO eventPayloadDTO = EventPayloadDTO.builder()
                    .payload(fieldMap)
                    .build();

            EventDTO eventDTO = EventDTO.builder()
                    .eventTypeId(Constants.EVENT_RIDE_REQUEST)
                    .eventDescription("RIDE REQUEST")
                    .timestamp(new Date())
                    .eventPayload(eventPayloadDTO)
                    .build();

            rabbitTemplate.convertAndSend(Constants.RMQ_EXCHANGE, Constants.RMQ_RK_RAW_API_REQUEST, eventDTO);
            LOGGER.info("ApplicationController::requestRide >> (QUALIFIED REQUEST) EVENT PUBLISHED TO QUEUE => {}", eventDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }
}
