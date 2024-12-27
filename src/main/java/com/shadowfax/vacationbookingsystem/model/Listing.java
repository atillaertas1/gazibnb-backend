package com.shadowfax.vacationbookingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "listings")
@Data
@JsonIgnoreProperties(value = {"user"}, ignoreUnknown = true)
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    private String category;

    @Column(name = "room_count")
    private Integer roomCount;

    @Column(name = "bathroom_count")
    private Integer bathroomCount;

    @Column(name = "guest_count")
    private Integer guestCount;

    @Embedded
    private Location location;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference("user-listings")  // Custom reference name
    private User user;

    private Double price;

    @Column(name = "image_url")
    private String imageUrl;  // Resmin bulunduğu dosyanın yolu

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
    @JsonManagedReference("listing-reservations")  // Custom reference name
    private List<Reservation> reservations;

}