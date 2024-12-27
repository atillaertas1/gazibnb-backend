package com.shadowfax.vacationbookingsystem.controller;

import com.shadowfax.vacationbookingsystem.model.Reservation;
import com.shadowfax.vacationbookingsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Create a new reservation
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = reservationService.createReservation(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    // Get all reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        // Veritabanından tüm rezervasyonları çekiyoruz
        List<Reservation> reservations = reservationService.getAllReservations();

        // Eğer rezervasyonlar boşsa, 204 No Content dönebiliriz
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Veritabanından alınan rezervasyonları döndürüyoruz
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Get reservation by id
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update an existing reservation
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationDetails);
        return updatedReservation != null ? new ResponseEntity<>(updatedReservation, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete a reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Get reservations by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Get reservations by listing id
    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Reservation>> getReservationsByListingId(@PathVariable Long listingId) {
        List<Reservation> reservations = reservationService.getReservationsByListingId(listingId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}