package com.shadowfax.vacationbookingsystem.repository;

import com.shadowfax.vacationbookingsystem.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUserId(Long userId);
}
