package com.shadowfax.vacationbookingsystem.service;

import com.shadowfax.vacationbookingsystem.model.UserFavorite;
import com.shadowfax.vacationbookingsystem.repository.UserFavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFavoriteService {

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    public void addFavorite(UserFavorite userFavorite) {
        System.out.println("userFavorite-listing: " + userFavorite.getListing());
        if (userFavorite.getUser() == null || userFavorite.getListing() == null) {
            throw new IllegalArgumentException("User or Listing cannot be null");
        }

        Optional<UserFavorite> existingFavorite = userFavoriteRepository.findByUserAndListing(
                userFavorite.getUser(), userFavorite.getListing()
        );

        if (existingFavorite.isPresent()) {
            throw new IllegalArgumentException("This favorite already exists.");
        }

        userFavoriteRepository.save(userFavorite);
    }

    // Get favorite by userId and listingId
    public UserFavorite getFavoriteByUserAndListing(Long userId, Long listingId) {
        return userFavoriteRepository.findByUserIdAndListingId(userId, listingId);
    }

    // Get all favorites for a user
    public Iterable<UserFavorite> getFavoritesByUser(Long userId) {
        return userFavoriteRepository.findByUserId(userId);
    }

    // Remove favorite by userId and listingId
    public String removeFavorite(Long userId, Long listingId) {
        UserFavorite userFavorite = userFavoriteRepository.findByUserIdAndListingId(userId, listingId);
        if (userFavorite != null) {
            userFavoriteRepository.delete(userFavorite);
            return "Favorite removed";
        }
        return "Favorite not found";
    }
}
