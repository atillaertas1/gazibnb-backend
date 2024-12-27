package com.shadowfax.vacationbookingsystem.service;

import com.shadowfax.vacationbookingsystem.model.Listing;
import com.shadowfax.vacationbookingsystem.model.User;
import com.shadowfax.vacationbookingsystem.repository.ListingRepository;
import com.shadowfax.vacationbookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;


    // Get all listings
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    // Get listing by ID
    public Listing getListingById(Long listingId) {
        return listingRepository.findById(listingId).orElse(null);
    }

    public Listing createListing(Listing listing) {
        if (listing.getUser() != null && listing.getUser().getId() != null) {
            Optional<User> userOptional = userRepository.findById(listing.getUser().getId());
            if (userOptional.isPresent()) {
                listing.setUser(userOptional.get());
            } else {
                throw new IllegalArgumentException("User not found");
            }
        }
        return listingRepository.save(listing);
    }

    // Update a listing
    public Listing updateListing(Long listingId, Listing listingDetails) {
        Listing existingListing = listingRepository.findById(listingId).orElse(null);
        if (existingListing != null) {
            existingListing.setTitle(listingDetails.getTitle());
            existingListing.setDescription(listingDetails.getDescription());
            existingListing.setCategory(listingDetails.getCategory());
            existingListing.setRoomCount(listingDetails.getRoomCount());
            existingListing.setBathroomCount(listingDetails.getBathroomCount());
            existingListing.setGuestCount(listingDetails.getGuestCount());
            existingListing.setLocation(listingDetails.getLocation());
            existingListing.setPrice(listingDetails.getPrice());
            existingListing.setImageUrl(listingDetails.getImageUrl());
            existingListing.setUpdatedAt(LocalDateTime.now());
            return listingRepository.save(existingListing);
        }
        return null;
    }

    // Delete a listing
    public boolean deleteListing(Long listingId) {
        Listing listing = listingRepository.findById(listingId).orElse(null);
        if (listing != null) {
            listingRepository.delete(listing);
            return true;
        }
        return false;
    }

    public List<Listing> getListingsByUserId(Long userId) {
        try {
            return listingRepository.findByUserId(userId);
        } catch (Exception e) {
            // Hata günlüğü
            throw new RuntimeException("Error retrieving listings for user: " + userId, e);
        }
    }

}
