package com.rrms.controller;

import com.rrms.model.Rental;
import com.rrms.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // Add a rental
    @PostMapping
    public Rental addRental(@Valid @RequestBody Rental rental) {
        return rentalService.addRental(rental);
    }

    // View all rentals
    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    // Update a rental
    @PutMapping("/{id}")
    public Rental updateRental(
            @PathVariable Long id,
            @Valid @RequestBody Rental rental) {
        return rentalService.updateRental(id, rental);
    }

    // Delete a rental
    @DeleteMapping("/{id}")
    public String deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return "Rental deleted successfully";
    }
}