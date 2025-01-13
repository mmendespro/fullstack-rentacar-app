package net.local.rentacar.domain.exceptions;

public class CarNotFountException extends DomainException {
    public CarNotFountException(String carId) {
        super(String.format("Car not found with id: %s", carId));
    }
}
