package com.rrms.service;

import com.rrms.model.Rental;
import com.rrms.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental addRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental updateRental(Long id, Rental updatedRental) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setReturnDate(updatedRental.getReturnDate());

        return rentalRepository.save(rental);
    }

    public void deleteRental(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new RuntimeException("Rental not found");
        }

        rentalRepository.deleteById(id);
    }
}