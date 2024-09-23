package com.ExpressLane.Service;

import com.ExpressLane.DTO.ShipmentStatusDTO;
import com.ExpressLane.Model.ShipmentPackage;
import com.ExpressLane.Model.ShipmentStatus;
import com.ExpressLane.Repository.PackageRepository;
import com.ExpressLane.Repository.ShipmentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class ShipmentStatusService {

    @Autowired
    private ShipmentStatusRepository shipmentStatusRepository;

    @Autowired
    private PackageRepository packageRepository;

    // Método para crear un estado de envío
    public ShipmentStatusDTO createShipmentStatus(ShipmentStatusDTO shipmentStatusDTO) {
        ShipmentStatus shipmentStatus = mapToEntity(shipmentStatusDTO);

        // Asegurarse de que el paquete existe antes de asignarlo al estado
        ShipmentPackage aPackage = packageRepository.findById(shipmentStatusDTO.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found with id " + shipmentStatusDTO.getPackageId()));

        shipmentStatus.setAPackage(aPackage);
        ShipmentStatus newShipmentStatus = shipmentStatusRepository.save(shipmentStatus);

        return mapToDTO(newShipmentStatus);
    }

    // Método para obtener todos los estados de envío
    public List<ShipmentStatusDTO> getAllShipmentStatus() {
        List<ShipmentStatus> shipmentStatuses = shipmentStatusRepository.findAll();
        return shipmentStatuses.stream().map(this::mapToDTO).toList();
    }

    // Método para obtener un estado de envío por ID
    public ShipmentStatusDTO getShipmentStatusById(Long id) {
        ShipmentStatus shipmentStatus = shipmentStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment status not found with id " + id));
        return mapToDTO(shipmentStatus);
    }

    // Método para actualizar un estado de envío existente
    public ShipmentStatusDTO updateShipmentStatus(Long id, ShipmentStatusDTO shipmentStatusDTO) {
        ShipmentStatus existingShipmentStatus = shipmentStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment status not found with id " + id));

        existingShipmentStatus.setStatus(shipmentStatusDTO.getStatus());

        ShipmentStatus updatedShipmentStatus = shipmentStatusRepository.save(existingShipmentStatus);
        return mapToDTO(updatedShipmentStatus);
    }

    // Método para eliminar un estado de envío por ID
    public void deleteShipmentStatus(Long id) {
        ShipmentStatus shipmentStatus = shipmentStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment status not found with id " + id));
        shipmentStatusRepository.delete(shipmentStatus);
    }

    // Método auxiliar para mapear entre ENTITY y DTO
    private ShipmentStatusDTO mapToDTO(ShipmentStatus shipmentStatus) {
        return ShipmentStatusDTO.builder()
                .id(shipmentStatus.getId())
                .packageId(shipmentStatus.getAPackage().getId())
                .status(shipmentStatus.getStatus())
                .updatedAt(shipmentStatus.getUpdateAt())
                .build();
    }

    private ShipmentStatus mapToEntity(ShipmentStatusDTO shipmentStatusDTO) {
        return ShipmentStatus.builder()
                .status(shipmentStatusDTO.getStatus())
                .updateAt(new Timestamp(System.currentTimeMillis())) // Asigna la fecha de actualización actual
                .build();
    }
}