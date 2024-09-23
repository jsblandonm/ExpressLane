package com.ExpressLane.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "packages")
public class ShipmentPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;


    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;


    @ManyToOne
    @JoinColumn(name = "origin_address_id", nullable = false)
    private Address originAddress;


    @ManyToOne
    @JoinColumn(name = "destination_address_id", nullable = false)
    private Address destinationAddress;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private String dimensions;

    @NotNull
    private String status;


    private Timestamp createdAt;
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreated(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
