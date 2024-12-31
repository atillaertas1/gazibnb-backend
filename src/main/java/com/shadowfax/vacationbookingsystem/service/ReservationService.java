package com.shadowfax.vacationbookingsystem.service;

import com.shadowfax.vacationbookingsystem.model.Listing;
import com.shadowfax.vacationbookingsystem.model.Reservation;
import com.shadowfax.vacationbookingsystem.model.User;
import com.shadowfax.vacationbookingsystem.repository.ListingRepository;
import com.shadowfax.vacationbookingsystem.repository.ReservationRepository;
import com.shadowfax.vacationbookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListingRepository listingRepository;

    // Create a new reservation
    public Reservation createReservation(Reservation reservation) {
        // Retrieve user and listing entities based on the userId and listingId
        Optional<User> userOptional = userRepository.findById(reservation.getUserId());
        Optional<Listing> listingOptional = listingRepository.findById(reservation.getListingId());

        if (!userOptional.isPresent() || !listingOptional.isPresent()) {
            throw new IllegalArgumentException("User or Listing not found");
        }

        // Set the user and listing on the reservation
        reservation.setUser(userOptional.get());
        reservation.setListing(listingOptional.get());

        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        reservation.setCreatedAt(now);
        reservation.setUpdatedAt(now);

        // Save the reservation
        return reservationRepository.save(reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Get reservation by id
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    // Update an existing reservation
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            reservation.setUserId(reservationDetails.getUserId());
            reservation.setListingId(reservationDetails.getListingId());
            reservation.setStartDate(reservationDetails.getStartDate());
            reservation.setEndDate(reservationDetails.getEndDate());
            reservation.setTotalPrice(reservationDetails.getTotalPrice());
            reservation.setCreatedAt(reservationDetails.getCreatedAt());
            return reservationRepository.save(reservation);
        }
        return null;
    }

    // Delete a reservation
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    // Get reservations for a specific listing
    public List<Reservation> getReservationsByListingId(Long listingId) {
        return reservationRepository.findByListingId(listingId);
    }
}
