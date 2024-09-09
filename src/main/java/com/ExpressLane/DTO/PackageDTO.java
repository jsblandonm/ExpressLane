package com.ExpressLane.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageDTO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long originAddressId;
    private Long destinationAddressId;
    private BigDecimal weight;
    private String dimensions;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
