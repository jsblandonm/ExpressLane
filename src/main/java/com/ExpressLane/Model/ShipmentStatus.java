package com.ExpressLane.Model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shipment_status")
public class ShipmentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private ShipmentPackage aPackage;

    @NonNull
    private String status;

    private Timestamp updateAt;


    @PreUpdate
    protected void onUpdate(){
        this.updateAt = new Timestamp(System.currentTimeMillis());
    }

}
