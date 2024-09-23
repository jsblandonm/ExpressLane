package com.ExpressLane.Service;

import com.ExpressLane.DTO.PackageDTO;
import com.ExpressLane.Model.Address;
import com.ExpressLane.Model.ShipmentPackage;
import com.ExpressLane.Model.User;
import com.ExpressLane.Repository.AddressRepository;
import com.ExpressLane.Repository.PackageRepository;
import com.ExpressLane.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    //Método para crear o guardar un nuevo paquete
    public PackageDTO createPackage(PackageDTO packageDTO) {
        ShipmentPackage shipmentPackage = mapToEntity(packageDTO);
        ShipmentPackage newShipmentPackage = packageRepository.save(shipmentPackage);
        return mapToDTO(newShipmentPackage);
    }

    //Método paa obtener todos los paquetes
    public List<PackageDTO> getAllPackages() {
        List<ShipmentPackage> shipmentPackages = packageRepository.findAll();
        return shipmentPackages.stream().map(this::mapToDTO).toList();
    }

    //Método para obtener un paquete por direccion ID
    public PackageDTO getPackageByID(Long id) {
        Optional<ShipmentPackage> aPackage = packageRepository.findById(id);
        if (aPackage.isPresent()) {
            return mapToDTO(aPackage.get());
        }
        throw new RuntimeException("Package not Found" + id);
    }

    //Método para actualizar un paquete por ID
    public PackageDTO updatePackage(Long id, PackageDTO packageDTO) {
        ShipmentPackage existingShipmentPackage = packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found with id" + id));
        existingShipmentPackage.setWeight(packageDTO.getWeight());
        existingShipmentPackage.setDimensions(packageDTO.getDimensions());
        existingShipmentPackage.setStatus(packageDTO.getStatus());

        ShipmentPackage updateShipmentPackage = packageRepository.save(existingShipmentPackage);
        return mapToDTO(updateShipmentPackage);
    }

    //Método para eliminar un paquete por ID
    public void deletePackage(Long id) {
        ShipmentPackage shipmentPackage = packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found with id" + id));
        packageRepository.delete(shipmentPackage);
    }

    //Método auxiliar para mapear enre Entity y DTO
    public PackageDTO mapToDTO(ShipmentPackage shipmentPackage) {
        if (shipmentPackage == null) {
            return null;
        }
        return PackageDTO.builder()
                .id(shipmentPackage.getId())
                .senderId(shipmentPackage.getSender().getId())
                .receiverId(shipmentPackage.getReceiver().getId())
                .originAddressId(shipmentPackage.getOriginAddress().getId())
                .destinationAddressId(shipmentPackage.getDestinationAddress().getId())
                .weight(shipmentPackage.getWeight())
                .dimensions(shipmentPackage.getDimensions())
                .status(shipmentPackage.getStatus())
                .createdAt(shipmentPackage.getCreatedAt())
                .updatedAt(shipmentPackage.getUpdatedAt())
                .build();
    }

    private ShipmentPackage mapToEntity(PackageDTO packageDTO) {
        ShipmentPackage shipmentPackage = ShipmentPackage.builder()
                .weight(packageDTO.getWeight())
                .dimensions(packageDTO.getDimensions())
                .status(packageDTO.getStatus())
                .build();
        //Configurar la relacón con User y Address
        shipmentPackage.setSender(new User(packageDTO.getSenderId()));
        shipmentPackage.setReceiver(new User(packageDTO.getReceiverId()));
        shipmentPackage.setOriginAddress(new Address(packageDTO.getOriginAddressId()));
        shipmentPackage.setDestinationAddress(new Address(packageDTO.getDestinationAddressId()));
        return shipmentPackage;

    }

}
