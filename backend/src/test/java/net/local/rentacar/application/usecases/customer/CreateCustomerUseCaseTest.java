package net.local.rentacar.application.usecases.customer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolationException;
import net.local.rentacar.application.dtos.CreateCustomerInput;
import net.local.rentacar.application.dtos.CreateCustomerOutput;
import net.local.rentacar.domain.entities.Customer;
import net.local.rentacar.domain.repositories.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomerUseCase createCustomerUseCase;

    @Test
    void whenCreateCustomer_thenShouldReturnCustomerOutput() {
        // Arrange
        String name = "John Doe";
        String document = "123.456.789-00";
        CreateCustomerInput input = new CreateCustomerInput(name, document);
        
        // Act
        CreateCustomerOutput output = createCustomerUseCase.execute(input);
        
        // Assert
        assertNotNull(output);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void whenCreateCustomer_thenShouldThrowExceptionCustomerOutputInvalidData() {
        // Arrange
        String name = "John Doe";
        String document = "";

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> {
            new CreateCustomerInput(name, document);
        });
    }
}
