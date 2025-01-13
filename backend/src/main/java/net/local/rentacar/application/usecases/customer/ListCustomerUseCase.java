package net.local.rentacar.application.usecases.customer;

import org.springframework.stereotype.Service;

import net.local.rentacar.application.dtos.CustomerOutput;
import net.local.rentacar.application.dtos.ListCustomerOutput;
import net.local.rentacar.application.usecases.UseCase;
import net.local.rentacar.application.usecases.UseCaseInput;
import net.local.rentacar.domain.repositories.CustomerRepository;

@Service
public class ListCustomerUseCase implements UseCase<UseCaseInput,ListCustomerOutput> {

    private final CustomerRepository repository;

    public ListCustomerUseCase(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public ListCustomerOutput execute(UseCaseInput input) {
        var customers = repository.findAll()
                                  .stream()
                                  .map(c -> new CustomerOutput(c.getId().toString(), c.getName(), c.getDocument(), c.getLoyaltyPoints()))
                                  .toList();
        return new ListCustomerOutput(customers);
    }
    
}
