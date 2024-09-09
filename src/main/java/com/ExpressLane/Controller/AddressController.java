package com.ExpressLane.Controller;

import com.ExpressLane.DTO.AddressDTO;
import com.ExpressLane.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Endpoint para crear una nueva dirección
    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO){
        AddressDTO createdAddress = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    // Endpoint para obtener todas las direcciones
    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        List<AddressDTO> addresses = addressService.getAllAdresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    // Endpoint para obtener una dirección por ID
    @GetMapping("/id")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id){
        AddressDTO address = addressService.getAddressByID(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    // Endpoint para actualizar una dirección existente
    @PutMapping("/id")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO){
        AddressDTO updatedAddress  = addressService.updateAddress(id, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    // Endpoint para eliminar una dirección por ID
    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
