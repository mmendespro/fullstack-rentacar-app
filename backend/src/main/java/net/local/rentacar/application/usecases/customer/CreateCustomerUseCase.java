package net.local.rentacar.application.usecases.customer;

import org.springframework.stereotype.Service;

import net.local.rentacar.application.dtos.CreateCustomerInput;
import net.local.rentacar.application.dtos.CreateCustomerOutput;
import net.local.rentacar.application.usecases.UseCase;
import net.local.rentacar.domain.entities.Customer;
import net.local.rentacar.domain.repositories.CustomerRepository;

@Service
public class CreateCustomerUseCase implements UseCase<CreateCustomerInput,CreateCustomerOutput> {

    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CreateCustomerOutput execute(CreateCustomerInput input) {
        var customer = new Customer(input.getName(), input.getDocument());
        customerRepository.save(customer);
        return new CreateCustomerOutput(customer.getId().toString());
    }
    
}
