package com.samiach.easytaxi.dto.event;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EventPayloadDTO {
    private Map<String, Object> payload;
}
