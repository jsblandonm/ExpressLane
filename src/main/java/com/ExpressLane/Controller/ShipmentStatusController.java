package com.ExpressLane.Controller;

import com.ExpressLane.DTO.ShipmentStatusDTO;
import com.ExpressLane.Service.ShipmentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipment-status")
public class ShipmentStatusController {
    @Autowired
    private ShipmentStatusService shipmentStatusService;

    // Endpoint para crear un nuevo estado de envío
    @PostMapping
    public ResponseEntity<ShipmentStatusDTO> createShipmentStatus(@RequestBody ShipmentStatusDTO shipmentStatusDTO) {
        ShipmentStatusDTO createdStatus = shipmentStatusService.createShipmentStatus(shipmentStatusDTO);
        return new ResponseEntity<>(createdStatus, HttpStatus.CREATED);
    }

    // Endpoint para obtener todos los estados de envío
    @GetMapping
    public ResponseEntity<List<ShipmentStatusDTO>> getAllShipmentStatuses() {
        List<ShipmentStatusDTO> shipmentStatuses = shipmentStatusService.getAllShipmentStatus();
        return new ResponseEntity<>(shipmentStatuses, HttpStatus.OK);
    }

    // Endpoint para obtener un estado de envío por ID
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentStatusDTO> getShipmentStatusById(@PathVariable Long id) {
        ShipmentStatusDTO shipmentStatus = shipmentStatusService.getShipmentStatusById(id);
        return new ResponseEntity<>(shipmentStatus, HttpStatus.OK);
    }

    // Endpoint para actualizar un estado de envío existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<ShipmentStatusDTO> updateShipmentStatus(
            @PathVariable Long id, @RequestBody ShipmentStatusDTO shipmentStatusDTO) {
        ShipmentStatusDTO updatedStatus = shipmentStatusService.updateShipmentStatus(id, shipmentStatusDTO);
        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }

    // Endpoint para eliminar un estado de envío por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipmentStatus(@PathVariable Long id) {
        shipmentStatusService.deleteShipmentStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
