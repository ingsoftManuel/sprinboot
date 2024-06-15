package co.edu.co.spring.demo;

import co.edu.co.spring.demo.domain.model.Vehicle;
import co.edu.co.spring.demo.mapping.DTO.VehiclesDTO;
import co.edu.co.spring.demo.mapping.mappers.VehiclesMapper;
import co.edu.co.spring.demo.repository.VehiclesRepository;
import co.edu.co.spring.demo.service.VehiclesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehiclesServiceTest {

    @Mock
    private VehiclesRepository vehiclesRepository;

    @Mock
    private VehiclesMapper vehiclesMapper;

    @InjectMocks
    private VehiclesService vehiclesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addVehicle() {
        VehiclesDTO vehiclesDTO = new VehiclesDTO("Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        Vehicle vehicle = new Vehicle(1, "Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");

        when(vehiclesMapper.mapFromDTO(vehiclesDTO)).thenReturn(vehicle);
        when(vehiclesRepository.save(vehicle)).thenReturn(vehicle);
        when(vehiclesMapper.mapFromModel(vehicle)).thenReturn(vehiclesDTO);

        VehiclesDTO result = vehiclesService.addVehicle(vehiclesDTO);

        assertNotNull(result);
        assertEquals(vehiclesDTO, result);
        verify(vehiclesRepository, times(1)).save(vehicle);
    }

    @Test
    void fetchVehicleById() {
        int id = 1;
        VehiclesDTO vehiclesDTO = new VehiclesDTO("Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        Vehicle vehicle = new Vehicle(id, "Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");

        when(vehiclesRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehiclesMapper.mapFromModel(vehicle)).thenReturn(vehiclesDTO);

        VehiclesDTO result = vehiclesService.fetchVehicleById(id);

        assertNotNull(result);
        assertEquals(vehiclesDTO, result);
        verify(vehiclesRepository, times(1)).findById(id);
    }

    @Test
    void fetchAllVehicles() {
        VehiclesDTO vehiclesDTO1 = new VehiclesDTO("Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        VehiclesDTO vehiclesDTO2 = new VehiclesDTO("Car", "Honda", "Civic", 2019, new BigDecimal("40.00"), "Available");
        Vehicle vehicle1 = new Vehicle(1, "Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        Vehicle vehicle2 = new Vehicle(2, "Car", "Honda", "Civic", 2019, new BigDecimal("40.00"), "Available");

        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        List<VehiclesDTO> vehiclesDTOs = Arrays.asList(vehiclesDTO1, vehiclesDTO2);

        when(vehiclesRepository.findAll()).thenReturn(vehicles);
        when(vehiclesMapper.mapFromModel(vehicle1)).thenReturn(vehiclesDTO1);
        when(vehiclesMapper.mapFromModel(vehicle2)).thenReturn(vehiclesDTO2);

        List<VehiclesDTO> result = vehiclesService.fetchAllVehicles();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(vehiclesDTOs, result);
        verify(vehiclesRepository, times(1)).findAll();
    }

    @Test
    void modifyVehicle() {
        int id = 1;
        VehiclesDTO vehiclesDTO = new VehiclesDTO("Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        Vehicle vehicle = new Vehicle(id, "Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");

        when(vehiclesRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehiclesMapper.mapFromDTO(vehiclesDTO)).thenReturn(vehicle);
        when(vehiclesRepository.save(vehicle)).thenReturn(vehicle);
        when(vehiclesMapper.mapFromModel(vehicle)).thenReturn(vehiclesDTO);

        VehiclesDTO result = vehiclesService.modifyVehicle(id, vehiclesDTO);

        assertNotNull(result);
        assertEquals(vehiclesDTO, result);
        verify(vehiclesRepository, times(1)).findById(id);
        verify(vehiclesRepository, times(1)).save(vehicle);
    }

    @Test
    void removeVehicle() {
        int id = 1;

        doNothing().when(vehiclesRepository).deleteById(id);

        vehiclesService.removeVehicle(id);

        verify(vehiclesRepository, times(1)).deleteById(id);
    }

    @Test
    void filterVehiclesByType() {
        String type = "Car";
        VehiclesDTO vehiclesDTO1 = new VehiclesDTO(type, "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        VehiclesDTO vehiclesDTO2 = new VehiclesDTO(type, "Honda", "Civic", 2019, new BigDecimal("40.00"), "Available");
        Vehicle vehicle1 = new Vehicle(1, type, "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        Vehicle vehicle2 = new Vehicle(2, type, "Honda", "Civic", 2019, new BigDecimal("40.00"), "Available");

        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        List<VehiclesDTO> vehiclesDTOs = Arrays.asList(vehiclesDTO1, vehiclesDTO2);

        when(vehiclesRepository.findByType(type)).thenReturn(vehicles);
        when(vehiclesMapper.mapFromModel(vehicle1)).thenReturn(vehiclesDTO1);
        when(vehiclesMapper.mapFromModel(vehicle2)).thenReturn(vehiclesDTO2);

        List<VehiclesDTO> result = vehiclesService.filterVehiclesByType(type);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(vehiclesDTOs, result);
        verify(vehiclesRepository, times(1)).findByType(type);
    }

    @Test
    void filterVehiclesByPriceRange() {
        BigDecimal minPrice = new BigDecimal("30.00");
        BigDecimal maxPrice = new BigDecimal("60.00");
        VehiclesDTO vehiclesDTO1 = new VehiclesDTO("Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        VehiclesDTO vehiclesDTO2 = new VehiclesDTO("Car", "Honda", "Civic", 2019, new BigDecimal("40.00"), "Available");
        Vehicle vehicle1 = new Vehicle(1, "Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), "Available");
        Vehicle vehicle2 = new Vehicle(2, "Car", "Honda", "Civic", 2019, new BigDecimal("40.00"), "Available");

        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        List<VehiclesDTO> vehiclesDTOs = Arrays.asList(vehiclesDTO1, vehiclesDTO2);

        when(vehiclesRepository.findByPricePerDayBetween(minPrice, maxPrice)).thenReturn(vehicles);
        when(vehiclesMapper.mapFromModel(vehicle1)).thenReturn(vehiclesDTO1);
        when(vehiclesMapper.mapFromModel(vehicle2)).thenReturn(vehiclesDTO2);

        List<VehiclesDTO> result = vehiclesService.filterVehiclesByPriceRange(minPrice, maxPrice);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(vehiclesDTOs, result);
        verify(vehiclesRepository, times(1)).findByPricePerDayBetween(minPrice, maxPrice);
    }

    @Test
    void filterVehiclesByStatus() {
        String status = "Available";
        VehiclesDTO vehiclesDTO1 = new VehiclesDTO("Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), status);
        VehiclesDTO vehiclesDTO2 = new VehiclesDTO("Car", "Honda", "Civic", 2019, new BigDecimal("40.00"), status);
        Vehicle vehicle1 = new Vehicle(1, "Car", "Toyota", "Camry", 2020, new BigDecimal("50.00"), status);
        Vehicle vehicle2 = new Vehicle(2, "Car", "Honda", "Civic", 2019, new BigDecimal("40.00"), status);

        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        List<VehiclesDTO> vehiclesDTOs = Arrays.asList(vehiclesDTO1, vehiclesDTO2);

        when(vehiclesRepository.findByStatus(status)).thenReturn(vehicles);
        when(vehiclesMapper.mapFromModel(vehicle1)).thenReturn(vehiclesDTO1);
        when(vehiclesMapper.mapFromModel(vehicle2)).thenReturn(vehiclesDTO2);

        List<VehiclesDTO> result = vehiclesService.filterVehiclesByStatus(status);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(vehiclesDTOs, result);
        verify(vehiclesRepository, times(1)).findByStatus(status);
    }
}
