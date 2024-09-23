package com.ExpressLane.Service;

import com.ExpressLane.DTO.AddressDTO;
import com.ExpressLane.Model.Address;
import com.ExpressLane.Model.User;
import com.ExpressLane.Repository.AddressRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRespository;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setId(1L);

        // Arrange
        address = Address.builder()
                .id(1L)
                .userId(user)
                .addressLine1("123 Main St")
                .addressLine2("Test Address 2")
                .city("Test City")
                .state("Test State")
                .postalCode("12345")
                .country("Test Country")
                .addressType("Test Type")
                .specialInstructions("Test Instructions")
                .contactName("Test Contact Name")
                .contactPhone("123456789")
                .addressLabel("Test Label")
                .build();

        addressDTO = AddressDTO.builder()
                .id(1L)
                .userId(1L)
                .addressLine1("123 Main St")
                .addressLine2("Test Address 2")
                .city("New York")
                .state("NY")
                .postalCode("10001")
                .country("USA")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .addresType("home")
                .contactName("John Doe")
                .contactPhone("123456789")
                .addressLabel("Home")
                .build();
    }

    @Test
    void createAddress() {
        // Act
        when(addressRespository.save(any(Address.class))).thenReturn(address);

        AddressDTO result = addressService.createAddress(addressDTO);

        // Assert
        assertNotNull(result);
        assertEquals(addressDTO.getAddressLine1(),result.getAddressLine1());

        verify(addressRespository).save(any(Address.class));
    }

    @Test
    void getAllAdresses() {

        // Arrange
        when(addressRespository.findAll()).thenReturn(List.of(address));

        List<AddressDTO> result = addressService.getAllAdresses();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(addressDTO.getAddressLine1(), result.getFirst().getAddressLine1());

        verify(addressRespository).findAll();
    }

    @Test
    void getAddressByID() {
        when(addressRespository.findById(1L)).thenReturn(Optional.of(address));

        AddressDTO result = addressService.getAddressByID(1L);

        assertNotNull(result);
        assertEquals("123 Main St", result.getAddressLine1());

        verify(addressRespository, times(1)).findById(1L);
    }

    @Test
    void updateAddress() {
        when(addressRespository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRespository.save(any(Address.class))).thenReturn(address);

        AddressDTO result = addressService.updateAddress(1L, addressDTO);

        assertNotNull(result);
        assertEquals(addressDTO.getAddressLine1(), result.getAddressLine1());

        verify(addressRespository, times(1)).findById(1L);
        verify(addressRespository,times(1)).save(any(Address.class));
    }

    @Test
    void deleteAddress() {
        when(addressRespository.findById(1L)).thenReturn(Optional.of(address));
        addressService.deleteAddress(1L);

        verify(addressRespository).delete(address);
    }

}