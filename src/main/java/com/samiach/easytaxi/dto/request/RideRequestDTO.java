package com.samiach.easytaxi.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RideRequestDTO {
    private int userId;
    private int userType;
    private String pickupBlockId;
    private String pickupLatitude;
    private String pickupLongitude;
    private String destinationBlockId;
    private String destinationLatitude;
    private String destinationLongitude;
}
