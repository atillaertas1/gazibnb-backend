package com.shadowfax.vacationbookingsystem.repository;

import com.shadowfax.vacationbookingsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT * FROM get_reservations_by_user_id(:userId)", nativeQuery = true)
    List<Reservation> findReservationsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM get_reservations_by_listing_id(:listingId)", nativeQuery = true)
    List<Reservation> findReservationsByListingId(@Param("listingId") Long listingId);

}

