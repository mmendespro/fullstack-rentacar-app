package net.local.rentacar.domain.exceptions;

public class CarNotAvailableException extends DomainException {
    public CarNotAvailableException(String carId) {
        super(String.format("Car not available with id: %s", carId));
    }
}
