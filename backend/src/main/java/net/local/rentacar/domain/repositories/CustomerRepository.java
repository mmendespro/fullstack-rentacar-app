package net.local.rentacar.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.local.rentacar.domain.entities.Customer;

public interface CustomerRepository {
    Optional<Customer> findById(UUID id);
    List<Customer> findAll();
    void save(Customer customer);
}
