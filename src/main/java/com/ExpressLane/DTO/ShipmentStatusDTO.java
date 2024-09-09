package com.ExpressLane.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentStatusDTO {
    private Long id;
    private Long packageId;
    private String status;
    private Timestamp updatedAt;
}
