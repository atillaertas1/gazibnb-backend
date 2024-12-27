package com.shadowfax.vacationbookingsystem.repository;

import com.shadowfax.vacationbookingsystem.model.Listing;
import com.shadowfax.vacationbookingsystem.model.User;
import com.shadowfax.vacationbookingsystem.model.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {

    boolean existsByUserAndListing(User user, Listing listing);

    UserFavorite findByUserIdAndListingId(Long userId, Long listingId);

    Iterable<UserFavorite> findByUserId(Long userId);

    Optional<UserFavorite> findByUserAndListing(User user, Listing listing);

}

