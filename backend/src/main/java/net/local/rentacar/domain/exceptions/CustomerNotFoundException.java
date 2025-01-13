package net.local.rentacar.domain.exceptions;

public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException(String customerId) {
        super(String.format("Customer not found with id: %s", customerId));
    }    
}
