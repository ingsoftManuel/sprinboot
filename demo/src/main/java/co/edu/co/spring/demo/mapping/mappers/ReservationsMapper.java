package co.edu.co.spring.demo.mapping.mappers;

import co.edu.co.spring.demo.domain.model.Reservation;
import co.edu.co.spring.demo.mapping.DTO.ReservationsDTO;
import co.edu.co.spring.demo.repository.UserRepository;
import co.edu.co.spring.demo.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationsMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehiclesRepository vehiclesRepository;

    public ReservationsDTO mapFromModel(Reservation reservation) {
        return ReservationsDTO.builder()
                .userId(reservation.getUser().getId())
                .vehicleId(reservation.getVehicle().getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .build();
    }

    public Reservation mapFromDTO(ReservationsDTO reservationsDTO) {
        return Reservation.builder()
                .user(userRepository.findById(reservationsDTO.userId()).orElseThrow(() -> new IllegalArgumentException("User not found")))
                .vehicle(vehiclesRepository.findById(reservationsDTO.vehicleId()).orElseThrow(() -> new IllegalArgumentException("Vehicle not found")))
                .startDate(reservationsDTO.startDate())
                .endDate(reservationsDTO.endDate())
                .build();
    }

}
