package co.edu.co.spring.demo.service;

import co.edu.co.spring.demo.domain.model.Reservation;
import co.edu.co.spring.demo.mapping.DTO.ReservationsDTO;
import co.edu.co.spring.demo.mapping.mappers.ReservationsMapper;
import co.edu.co.spring.demo.repository.ReservationsRepository;
import co.edu.co.spring.demo.repository.UserRepository;
import co.edu.co.spring.demo.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationsService {

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private ReservationsMapper reservationsMapper;

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private UserRepository userRepository;

    public ReservationsDTO createReservation(ReservationsDTO reservationsDTO) {
        Reservation reservation = reservationsMapper.mapFromDTO(reservationsDTO);
        calculateAndSetTotalCost(reservation);
        return saveAndMapReservation(reservation);
    }

    public ReservationsDTO getReservationById(Integer id) {
        return reservationsRepository.findById(id)
                .map(reservationsMapper::mapFromModel)
                .orElse(null);
    }

    public List<ReservationsDTO> getAllReservations() {
        return reservationsRepository.findAll().stream()
                .map(reservationsMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    public ReservationsDTO updateReservation(Integer id, ReservationsDTO reservationsDTO) {
        return reservationsRepository.findById(id)
                .map(existingReservation -> updateExistingReservation(existingReservation, reservationsDTO))
                .map(reservationsRepository::save)
                .map(reservationsMapper::mapFromModel)
                .orElse(null);
    }

    public void deleteReservation(Integer id) {
        reservationsRepository.deleteById(id);
    }

    public boolean isVehicleAvailable(Integer vehicleId, LocalDate startDate, LocalDate endDate) {
        return reservationsRepository.findByVehicleIdAndDateRange(vehicleId, startDate, endDate).isEmpty();
    }

    public String selectRentalDates(Integer vehicleId, LocalDate startDate, LocalDate endDate) {
        return isVehicleAvailable(vehicleId, startDate, endDate) ?
                "El vehículo está disponible para las fechas seleccionadas." :
                "El vehículo no está disponible para las fechas seleccionadas.";
    }

    public List<ReservationsDTO> getReservationsByUserId(Integer userId) {
        return reservationsRepository.findByUserId(userId).stream()
                .map(reservationsMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    public List<ReservationsDTO> getReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return reservationsRepository.findByStartDateBetween(startDate, endDate).stream()
                .map(reservationsMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    private void calculateAndSetTotalCost(Reservation reservation) {
        long days = ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());
        BigDecimal totalCost = reservation.getVehicle().getPricePerDay().multiply(BigDecimal.valueOf(days));
        reservation.setTotalCost(totalCost);
    }

    private Reservation updateExistingReservation(Reservation existingReservation, ReservationsDTO reservationsDTO) {
        existingReservation.setUser(userRepository.findById(reservationsDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        existingReservation.setVehicle(vehiclesRepository.findById(reservationsDTO.vehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found")));
        existingReservation.setStartDate(reservationsDTO.startDate());
        existingReservation.setEndDate(reservationsDTO.endDate());
        calculateAndSetTotalCost(existingReservation);
        return existingReservation;
    }

    private ReservationsDTO saveAndMapReservation(Reservation reservation) {
        reservation = reservationsRepository.save(reservation);
        return reservationsMapper.mapFromModel(reservation);
    }
}
