package co.edu.co.spring.demo.controllers;

import co.edu.co.spring.demo.mapping.DTO.VehiclesDTO;
import co.edu.co.spring.demo.service.VehiclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehiclesController {

    @Autowired
    private VehiclesService vehiclesService;

    @PostMapping
    public ResponseEntity<VehiclesDTO> addVehicle(@RequestBody VehiclesDTO vehiclesDTO) {
        VehiclesDTO createdVehicle = vehiclesService.addVehicle(vehiclesDTO);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiclesDTO> fetchVehicleById(@PathVariable Integer id) {
        VehiclesDTO vehicle = vehiclesService.fetchVehicleById(id);
        return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<VehiclesDTO>> fetchAllVehicles() {
        List<VehiclesDTO> vehicles = vehiclesService.fetchAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiclesDTO> modifyVehicle(@PathVariable Integer id, @RequestBody VehiclesDTO vehiclesDTO) {
        VehiclesDTO updatedVehicle = vehiclesService.modifyVehicle(id, vehiclesDTO);
        return updatedVehicle != null ? ResponseEntity.ok(updatedVehicle) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeVehicle(@PathVariable Integer id) {
        vehiclesService.removeVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type")
    public ResponseEntity<List<VehiclesDTO>> filterVehiclesByType(@RequestParam String type) {
        List<VehiclesDTO> vehicles = vehiclesService.filterVehiclesByType(type);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/price")
    public ResponseEntity<List<VehiclesDTO>> filterVehiclesByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<VehiclesDTO> vehicles = vehiclesService.filterVehiclesByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/status")
    public ResponseEntity<List<VehiclesDTO>> filterVehiclesByStatus(@RequestParam String status) {
        List<VehiclesDTO> vehicles = vehiclesService.filterVehiclesByStatus(status);
        return ResponseEntity.ok(vehicles);
    }
}
