package net.local.rentacar.infrastructure.repositories.inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import net.local.rentacar.domain.entities.Car;
import net.local.rentacar.domain.repositories.CarRepository;

@Repository
public class InMemoryCarRepository implements CarRepository {

    private final Map<UUID, Car> cars = new HashMap<>();

    @Override
    public Optional<Car> findById(UUID id) {
        return Optional.ofNullable(cars.get(id));
    }

    @Override
    public void save(Car car) {
        cars.put(car.getId(), car);
    }
}