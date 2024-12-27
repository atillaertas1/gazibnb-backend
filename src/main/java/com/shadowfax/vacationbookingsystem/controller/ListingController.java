package com.shadowfax.vacationbookingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shadowfax.vacationbookingsystem.model.Listing;
import com.shadowfax.vacationbookingsystem.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Value("${app.upload.dir:${user.home}}")  // File upload directory
    private String uploadDir;  // This will store the path where files are uploaded

    // Get all listings
    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        try {
            List<Listing> listings = listingService.getAllListings();
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get listing by ID
    @GetMapping("/{listingId}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long listingId) {
        try {
            Listing listing = listingService.getListingById(listingId);
            if (listing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(listing);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Create a new listing with image upload
    @PostMapping
    public ResponseEntity<?> createListing(
            @RequestParam("listing") String listingJson,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Parse the listing object from JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Listing listing = objectMapper.readValue(listingJson, Listing.class);

            // If the image file is provided, save it to the local file system
            if (imageFile != null && !imageFile.isEmpty()) {
                // Generate the file path
                String fileName = listing.getUserId() + "_listing_" + listing.getId() + "_" + UUID.randomUUID() + ".jpg"; // UUID kullanarak benzersiz bir dosya adı oluşturuluyor
                Path uploadPath = Paths.get(uploadDir, fileName);

                // Create the upload directory if it doesn't exist
                if (!Files.exists(uploadPath.getParent())) {
                    Files.createDirectories(uploadPath.getParent());
                }

                // Save the file to the local file system
                Files.copy(imageFile.getInputStream(), uploadPath);

                // Store the file path in the listing object
                listing.setImageUrl(uploadPath.toString());
            }

            // Save the listing in the database
            Listing savedListing = listingService.createListing(listing);
            return new ResponseEntity<>(savedListing, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a listing
    @PutMapping("/{listingId}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long listingId, @RequestBody Listing listingDetails) {
        try {
            Listing updatedListing = listingService.updateListing(listingId, listingDetails);
            if (updatedListing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(updatedListing);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Delete a listing by ID
    @DeleteMapping("/{listingId}")
    public ResponseEntity<String> deleteListing(@PathVariable Long listingId) {
        try {
            boolean isDeleted = listingService.deleteListing(listingId);
            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found.");
            }
            return ResponseEntity.ok("Listing deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the listing.");
        }
    }

    // Get listings by user ID (for reservations)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Listing>> getListingsByUserId(@PathVariable Long userId) {
        try {
            List<Listing> listings = listingService.getListingsByUserId(userId);
            if (listings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
