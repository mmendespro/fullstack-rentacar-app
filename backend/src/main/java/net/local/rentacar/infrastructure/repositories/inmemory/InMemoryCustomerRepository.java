package net.local.rentacar.infrastructure.repositories.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import net.local.rentacar.domain.entities.Customer;
import net.local.rentacar.domain.repositories.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<UUID, Customer> customers = new HashMap<>();

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }
    
    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(customers.get(id));
    }

    @Override
    public void save(Customer customer) {
        customers.put(customer.getId(), customer);
    }

}