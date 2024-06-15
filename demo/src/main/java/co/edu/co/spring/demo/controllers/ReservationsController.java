package co.edu.co.spring.demo.controllers;

import co.edu.co.spring.demo.mapping.DTO.ReservationsDTO;
import co.edu.co.spring.demo.service.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @PostMapping("/create")
    public ResponseEntity<ReservationsDTO> createReservation(@RequestBody ReservationsDTO reservationsDTO) {
        ReservationsDTO createdReservation = reservationsService.createReservation(reservationsDTO);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @GetMapping("search/{id}")
    public ResponseEntity<ReservationsDTO> getReservationById(@PathVariable Integer id) {
        ReservationsDTO reservation = reservationsService.getReservationById(id);
        return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReservationsDTO>> getAllReservations() {
        List<ReservationsDTO> reservations = reservationsService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ReservationsDTO> updateReservation(@PathVariable Integer id, @RequestBody ReservationsDTO reservationsDTO) {
        ReservationsDTO updatedReservation = reservationsService.updateReservation(id, reservationsDTO);
        return updatedReservation != null ? ResponseEntity.ok(updatedReservation) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationsService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/availability")
    public ResponseEntity<String> checkVehicleAvailability(
            @RequestParam Integer vehicleId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        String availability = reservationsService.selectRentalDates(vehicleId, startDate, endDate);
        return ResponseEntity.ok(availability);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationsDTO>> getReservationsByUserId(@PathVariable Integer userId) {
        List<ReservationsDTO> reservations = reservationsService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<ReservationsDTO>> getReservationsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<ReservationsDTO> reservations = reservationsService.getReservationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(reservations);
    }
}
