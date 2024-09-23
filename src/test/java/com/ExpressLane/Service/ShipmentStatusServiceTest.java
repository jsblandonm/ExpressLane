package com.ExpressLane.Service;

import com.ExpressLane.DTO.ShipmentStatusDTO;
import com.ExpressLane.Model.ShipmentPackage;
import com.ExpressLane.Model.ShipmentStatus;
import com.ExpressLane.Repository.PackageRepository;
import com.ExpressLane.Repository.ShipmentStatusRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentStatusServiceTest {

    @Mock
    private ShipmentStatusRepository shipmentStatusRepository;

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private ShipmentStatusService shipmentStatusService;

    private ShipmentPackage shipmentPackage;
    private ShipmentStatus shipmentStatus;
    private ShipmentStatusDTO shipmentStatusDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        shipmentPackage = ShipmentPackage.builder()
                .id(1L)
                .build();

        shipmentStatus = ShipmentStatus.builder()
                .id(1L)
                .aPackage(shipmentPackage)
                .status("Pending")
                .updateAt(new Timestamp(System.currentTimeMillis()))
                .build();

        shipmentStatusDTO = ShipmentStatusDTO.builder()
                .id(1L)
                .packageId(1L)
                .status("Pending")
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }


    @Test
    void createShipmentStatus() {
        when(packageRepository.findById(1L)).thenReturn(Optional.of(shipmentPackage));
        when(shipmentStatusRepository.save(any(ShipmentStatus.class))).thenReturn(shipmentStatus);

        ShipmentStatusDTO result = shipmentStatusService.createShipmentStatus(shipmentStatusDTO);

        assertNotNull(result);
        assertEquals(shipmentStatusDTO.getStatus(), result.getStatus());

        verify(packageRepository, times(1)).findById(1L);
        verify(shipmentStatusRepository, times(1)).save(any(ShipmentStatus.class));

    }

    @Test
    void getAllShipmentStatus() {
        when(shipmentStatusRepository.findAll()).thenReturn(List.of(shipmentStatus));

        List<ShipmentStatusDTO> result = shipmentStatusService.getAllShipmentStatus();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pending", result.getFirst().getStatus());

        verify(shipmentStatusRepository, times(1)).findAll();
    }

    @Test
    void getShipmentStatusById() {
        when(shipmentStatusRepository.findById(1L)).thenReturn(Optional.of(shipmentStatus));

        ShipmentStatusDTO result = shipmentStatusService.getShipmentStatusById(1L);

        assertNotNull(result);
        assertEquals(shipmentStatus.getStatus(), result.getStatus());

        verify(shipmentStatusRepository, times(1)).findById(1L);
    }

    @Test
    void updateShipmentStatus() {
        when(shipmentStatusRepository.findById(1L)).thenReturn(Optional.of(shipmentStatus));
        when(shipmentStatusRepository.save(any(ShipmentStatus.class))).thenReturn(shipmentStatus);

        ShipmentStatusDTO result = shipmentStatusService.updateShipmentStatus(1L, shipmentStatusDTO);

        assertNotNull(result);
        assertEquals(shipmentStatusDTO.getStatus(), result.getStatus());

        verify(shipmentStatusRepository, times(1)).findById(1L);
        verify(shipmentStatusRepository, times(1)).save(any(ShipmentStatus.class));
    }

    @Test
    void deleteShipmentStatus() {
        when(shipmentStatusRepository.findById(1L)).thenReturn(Optional.of(shipmentStatus));

        shipmentStatusService.deleteShipmentStatus(1L);

        verify(shipmentStatusRepository, times(1)).findById(1L);
        verify(shipmentStatusRepository, times(1)).delete(shipmentStatus);
    }
}