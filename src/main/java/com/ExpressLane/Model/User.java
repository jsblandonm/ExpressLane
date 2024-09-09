package com.ExpressLane.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(unique = true)
    private String email;
    private String phone;
    private Timestamp createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<ShipmentPackage> sentPackages;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<ShipmentPackage> receivedPackages;


    @PrePersist
    protected void onCreate(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    private boolean deleted = false;

    public User(Long id) {
        this.id = id;
    }

}
