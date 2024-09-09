package com.ExpressLane.Repository;

import com.ExpressLane.Model.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentStatusRepository extends JpaRepository<ShipmentStatus, Long> {
}
