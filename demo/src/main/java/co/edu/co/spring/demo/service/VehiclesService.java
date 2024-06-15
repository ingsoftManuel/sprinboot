package co.edu.co.spring.demo.service;

import co.edu.co.spring.demo.domain.model.Vehicle;
import co.edu.co.spring.demo.mapping.DTO.VehiclesDTO;
import co.edu.co.spring.demo.mapping.mappers.VehiclesMapper;
import co.edu.co.spring.demo.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiclesService {

    @Autowired
    private VehiclesRepository vehiclesRepository;

    public VehiclesDTO addVehicle(VehiclesDTO vehiclesDTO) {
        Vehicle vehicle = VehiclesMapper.mapFromDTO(vehiclesDTO);
        return saveAndMapVehicle(vehicle);
    }

    public VehiclesDTO fetchVehicleById(Integer id) {
        return vehiclesRepository.findById(id)
                .map(VehiclesMapper::mapFromModel)
                .orElse(null);
    }

    public List<VehiclesDTO> fetchAllVehicles() {
        return vehiclesRepository.findAll().stream()
                .map(VehiclesMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    public VehiclesDTO modifyVehicle(Integer id, VehiclesDTO vehiclesDTO) {
        return vehiclesRepository.findById(id)
                .map(existingVehicle -> updateVehicleDetails(existingVehicle, vehiclesDTO))
                .map(vehiclesRepository::save)
                .map(VehiclesMapper::mapFromModel)
                .orElse(null);
    }

    public void removeVehicle(Integer id) {
        vehiclesRepository.deleteById(id);
    }

    public List<VehiclesDTO> filterVehiclesByType(String type) {
        return vehiclesRepository.findByType(type).stream()
                .map(VehiclesMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    public List<VehiclesDTO> filterVehiclesByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return vehiclesRepository.findByPricePerDayBetween(minPrice, maxPrice).stream()
                .map(VehiclesMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    public List<VehiclesDTO> filterVehiclesByStatus(String status) {
        return vehiclesRepository.findByStatus(status).stream()
                .map(VehiclesMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    private Vehicle updateVehicleDetails(Vehicle existingVehicle, VehiclesDTO vehiclesDTO) {
        existingVehicle.setType(vehiclesDTO.type());
        existingVehicle.setMake(vehiclesDTO.make());
        existingVehicle.setModel(vehiclesDTO.model());
        existingVehicle.setYear(vehiclesDTO.year());
        existingVehicle.setPricePerDay(vehiclesDTO.pricePerDay());
        existingVehicle.setStatus(vehiclesDTO.status());
        return existingVehicle;
    }

    private VehiclesDTO saveAndMapVehicle(Vehicle vehicle) {
        vehicle = vehiclesRepository.save(vehicle);
        return VehiclesMapper.mapFromModel(vehicle);
    }
}
