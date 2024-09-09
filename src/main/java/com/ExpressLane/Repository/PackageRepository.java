package com.ExpressLane.Repository;

import com.ExpressLane.Model.ShipmentPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<ShipmentPackage, Long> {
}
