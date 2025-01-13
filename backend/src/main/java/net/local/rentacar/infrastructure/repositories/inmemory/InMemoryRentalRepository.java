package net.local.rentacar.infrastructure.repositories.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import net.local.rentacar.domain.entities.Rental;
import net.local.rentacar.domain.repositories.RentalRepository;
import net.local.rentacar.domain.vo.RentalStatus;

@Repository
public class InMemoryRentalRepository implements RentalRepository {

    private final Map<UUID, Rental> rentals = new HashMap<>();

    @Override
    public Optional<Rental> findById(UUID id) {
        return Optional.ofNullable(rentals.get(id));
    }
    
    @Override
    public List<Rental> listRentalByStatus(RentalStatus status) {
        return rentals.values().stream().filter(r -> r.getStatus() == status).toList();
    }

    @Override
    public void save(Rental rental) {
        rentals.put(rental.getId(), rental);
    }
}