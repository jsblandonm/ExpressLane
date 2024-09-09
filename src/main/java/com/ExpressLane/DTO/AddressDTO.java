package com.ExpressLane.DTO;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private Long id;
    private Long userId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Timestamp createdAt;
    private String addresType; // 'origin' or 'destination'
    private String specialInstructions;
    private String contactName;
    private String contactPhone;
    private String addressLabel; // 'Home', 'Office', etc...




}

