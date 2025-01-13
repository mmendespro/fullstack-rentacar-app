package net.local.rentacar.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.local.rentacar.domain.entities.Rental;
import net.local.rentacar.domain.vo.RentalStatus;

public interface RentalRepository {
    
    Optional<Rental> findById(UUID id);
    List<Rental> listRentalByStatus(RentalStatus status);
    void save(Rental rental);
}
