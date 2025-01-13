package net.local.rentacar.domain.exceptions;

public class RentalNotFoundException extends DomainException {
    public RentalNotFoundException(String rentalId) {
        super(String.format("Rental not found with id: %s", rentalId));
    }
}
