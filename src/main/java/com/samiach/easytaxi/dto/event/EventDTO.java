package com.samiach.easytaxi.dto.event;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EventDTO {
    private int eventTypeId;
    private String eventDescription;
    private Date timestamp;
    private EventPayloadDTO eventPayload;
}
