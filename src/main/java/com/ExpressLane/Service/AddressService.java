package com.ExpressLane.Service;

import com.ExpressLane.DTO.AddressDTO;
import com.ExpressLane.Model.Address;
import com.ExpressLane.Model.User;
import com.ExpressLane.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    //Metodo Para crear o guardar una nueva direccion
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = mapToEntity(addressDTO);
        Address newAddress = addressRepository.save(address);
        return mapToDTO(newAddress);
    }

    //Método para obteer todas las direcciones
    public List<AddressDTO> getAllAdresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(this::mapToDTO).toList();
    }

    // Método para obtener una dirección por ID
    public AddressDTO getAddressByID(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return mapToDTO(address.get());
        }
        throw new RuntimeException("Address not found with id" + id);
    }

    //Método para actulizar una dirección existente
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id" + id));
        existingAddress.setAddressLine1(addressDTO.getAddressLine1());
        existingAddress.setAddressLine2(addressDTO.getAddressLine2());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setPostalCode(addressDTO.getPostalCode());
        existingAddress.setCountry(addressDTO.getCountry());
        existingAddress.setAddressType(addressDTO.getAddresType());
        existingAddress.setSpecialInstructions(addressDTO.getSpecialInstructions());
        existingAddress.setContactName(addressDTO.getContactName());
        existingAddress.setContactPhone(addressDTO.getContactPhone());
        existingAddress.setAddressLabel(addressDTO.getAddressLabel());

        Address updateAddress = addressRepository.save(existingAddress);
        return mapToDTO(updateAddress);
    }

    //Método para eliminar una dorección por ID
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id" + id));
        addressRepository.delete(address);
    }

    //Método auxiliar para mapear entre Entity y DTO
    private AddressDTO mapToDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .userId(address.getUserId().getId())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .createdAt(address.getCreatedAt())
                .addresType(address.getAddressType())
                .specialInstructions(address.getSpecialInstructions())
                .contactName(address.getContactName())
                .contactPhone(address.getContactPhone())
                .addressLabel(address.getAddressLabel())
                .build();
    }

    private Address mapToEntity(AddressDTO addressDTO) {
        // Crear la entidad Address usando los valores del DTO
        Address address = Address.builder()
                .addressLine1(addressDTO.getAddressLine1())
                .addressLine2(addressDTO.getAddressLine2())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .postalCode(addressDTO.getPostalCode())
                .country(addressDTO.getCountry())
                .addressType(addressDTO.getAddresType())
                .specialInstructions(addressDTO.getSpecialInstructions())
                .contactName(addressDTO.getContactName())
                .contactPhone(addressDTO.getContactPhone())
                .addressLabel(addressDTO.getAddressLabel())
                .build();

        // Validar y asignar el User a la dirección
        if (addressDTO.getUserId() != null) {
            address.setUserId(new User(addressDTO.getUserId()));
        } else {
            throw new IllegalArgumentException("UserId cannot be null");
        }

        return address;
    }

}
