package com.ExpressLane.Service;

import com.ExpressLane.DTO.PackageDTO;
import com.ExpressLane.Model.Address;
import com.ExpressLane.Model.ShipmentPackage;
import com.ExpressLane.Model.User;
import com.ExpressLane.Repository.AddressRepository;
import com.ExpressLane.Repository.PackageRepository;
import com.ExpressLane.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PackageService packageService;

    private ShipmentPackage shipmentPackage;
    private PackageDTO packageDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User sender = new User(1L);
        User receiver = new User(2L);
        Address originAddress = new Address(1L);
        Address destinationAddress = new Address(1L);

        ShipmentPackage shipmentPackage = new ShipmentPackage();
        shipmentPackage.setId(1L);

        shipmentPackage = ShipmentPackage.builder()
                .id(1L)
                .sender(sender)
                .receiver(receiver)
                .originAddress(originAddress)
                .destinationAddress(destinationAddress)
                .weight(BigDecimal.valueOf(10.0))
                .dimensions("10x10x10")
                .status("Pending")
                .build();

        packageDTO = PackageDTO.builder()
                .id(1L)
                .senderId(1L)
                .receiverId(2L)
                .originAddressId(1L)
                .destinationAddressId(2L)
                .weight(BigDecimal.valueOf(10.0))
                .dimensions("10x10x10")
                .status("Pending")
                .build();
    }

    @Test
    void createPackage() {
        // Arrange
        when(packageRepository.save(any(ShipmentPackage.class))).thenReturn(shipmentPackage);

        PackageDTO result = packageService.createPackage(packageDTO);

        assertNotNull(result);
        assertEquals(packageDTO.getWeight(), result.getWeight());

        verify(packageRepository, times(1)).save(any(ShipmentPackage.class));
    }

    @Test
    void getAllPackages() {

        when(packageRepository.findAll()).thenReturn(List.of(shipmentPackage));

        List<PackageDTO> result = packageService.getAllPackages();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("pending", result.getFirst().getStatus());

        verify(packageRepository, times(1)).findAll();

    }

    @Test
    void getPackageByID() {
        when(packageRepository.findById(1L)).thenReturn(Optional.of(shipmentPackage));

        PackageDTO result = packageService.getPackageByID(1L);

        assertNotNull(result);
        assertEquals(shipmentPackage.getWeight(), result.getWeight());

        verify(packageRepository, times(1)).findById(1L);
    }

    @Test
    void updatePackage() {
        when(packageRepository.findById(1L)).thenReturn(Optional.of(shipmentPackage));
        when(packageRepository.save(any(ShipmentPackage.class))).thenReturn(shipmentPackage);

        PackageDTO result = packageService.updatePackage(1L, packageDTO);

        assertNotNull(result);
        assertEquals(packageDTO.getWeight(), result.getWeight());

        verify(packageRepository, times(1)).findById(1L);
        verify(packageRepository, times(1)).save(any(ShipmentPackage.class));
    }

    @Test
    void deletePackage() {
        when(packageRepository.findById(1L)).thenReturn(Optional.of(shipmentPackage));

        packageService.deletePackage(1L);

        verify(packageRepository, times(1)).findById(1L);
        verify(packageRepository, times(1)).delete(shipmentPackage);
    }
}