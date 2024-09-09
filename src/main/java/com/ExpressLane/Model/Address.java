package com.ExpressLane.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @NonNull
    private String addressLine1;

    private String addressLine2;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String postalCode;

    @NonNull
    private String country;

    private Timestamp createdAt;

    private String addressType; // 'origin' or 'destination'
    private String specialInstructions;
    private String contactName;
    private String contactPhone;
    private String addressLabel; // 'Home', 'Offie', etc...


    @PrePersist
    protected void onCreated(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Address(Long id) {
        this.id = id;
    }
}
