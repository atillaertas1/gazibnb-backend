package com.shadowfax.vacationbookingsystem.controller;

import com.shadowfax.vacationbookingsystem.model.UserFavorite;
import com.shadowfax.vacationbookingsystem.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/favorites")
public class UserFavoriteController {

    @Autowired
    private UserFavoriteService userFavoriteService;

    @PostMapping("/addFavorite")
    public ResponseEntity<?> addFavorite(@RequestBody UserFavorite userFavorite) {
        System.out.println("Received UserFavorite: " + userFavorite);
        try {
            // Null kontrolü ve ID doğrulaması
            if (userFavorite == null ||
                    userFavorite.getUser() == null ||
                    userFavorite.getUser().getId() == null ||
                    userFavorite.getListing() == null ||
                    userFavorite.getListing().getId() == null) {
                return ResponseEntity.badRequest().body("User ID and Listing ID cannot be null");
            }

            // Favori ekleme işlemi
            userFavoriteService.addFavorite(userFavorite);

            return ResponseEntity.ok("Favorite added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }


    // Get favorite by userId and listingId
    @GetMapping("/{userId}/{listingId}")
    public ResponseEntity<UserFavorite> getFavoriteByUserAndListing(@PathVariable Long userId, @PathVariable Long listingId) {
        try {
            UserFavorite userFavorite = userFavoriteService.getFavoriteByUserAndListing(userId, listingId);
            return userFavorite != null ?
                    ResponseEntity.ok(userFavorite) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get all favorites for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<UserFavorite>> getFavoritesByUser(@PathVariable Long userId) {
        try {
            Iterable<UserFavorite> favorites = userFavoriteService.getFavoritesByUser(userId);
            return ResponseEntity.ok(favorites);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete favorite by userId and listingId
    @DeleteMapping("/remove/{userId}/{listingId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long userId, @PathVariable Long listingId) {
        try {
            String result = userFavoriteService.removeFavorite(userId, listingId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
