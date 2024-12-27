package com.shadowfax.vacationbookingsystem.repository;

import com.shadowfax.vacationbookingsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByListingId(Long listingId);
}
