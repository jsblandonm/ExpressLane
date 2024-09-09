package com.ExpressLane.Controller;


import com.ExpressLane.DTO.PackageDTO;
import com.ExpressLane.Service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    // Endpoint para crear un nuevo paquete
    @PostMapping
    public ResponseEntity<PackageDTO> createPackage(@RequestBody PackageDTO packageDTO) {
        PackageDTO createdPackage = packageService.createPackage(packageDTO);
        return new ResponseEntity<>(createdPackage, HttpStatus.CREATED);
    }

    // Endpoint para obtener todos los paquetes
    @GetMapping
    public ResponseEntity<List<PackageDTO>> getAllPackages() {
        List<PackageDTO> packages = packageService.getAllPackages();
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    // Endpoint para obtener un paquete por ID
    @GetMapping("/{id}")
    public ResponseEntity<PackageDTO> getPackageById(@PathVariable Long id) {
        PackageDTO aPackage = packageService.getPackageByID(id);
        return new ResponseEntity<>(aPackage, HttpStatus.OK);
    }

    // Endpoint para actualizar un paquete existente
    @PutMapping("/{id}")
    public ResponseEntity<PackageDTO> updatePackage(@PathVariable Long id, @RequestBody PackageDTO packageDTO) {
        PackageDTO updatedPackage = packageService.updatePackage(id, packageDTO);
        return new ResponseEntity<>(updatedPackage, HttpStatus.OK);
    }

    // Endpoint para eliminar un paquete por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


