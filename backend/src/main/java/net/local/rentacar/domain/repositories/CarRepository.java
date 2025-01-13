package net.local.rentacar.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import net.local.rentacar.domain.entities.Car;

public interface CarRepository {
    Optional<Car> findById(UUID id);
    void save(Car car);
}
