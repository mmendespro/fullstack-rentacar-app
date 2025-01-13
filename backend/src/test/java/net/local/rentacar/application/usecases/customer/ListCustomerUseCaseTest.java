package net.local.rentacar.application.usecases.customer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.local.rentacar.application.dtos.ListCustomerOutput;
import net.local.rentacar.application.usecases.UseCaseInput;
import net.local.rentacar.domain.entities.Customer;
import net.local.rentacar.domain.repositories.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class ListCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ListCustomerUseCase listCustomerUseCase;

    @Test
    void whenListCustomers_thenShouldReturnListOfCustomers() {
        // Arrange
        Customer customer1 = new Customer("John Doe", "123.456.789-00");
        Customer customer2 = new Customer("Jane Doe", "987.654.321-00");
        List<Customer> customers = Arrays.asList(customer1, customer2);
        
        when(customerRepository.findAll()).thenReturn(customers);
        
        // Act
        ListCustomerOutput output = listCustomerUseCase.execute(new UseCaseInput() {});
        
        // Assert
        assertNotNull(output);
        assertEquals(2, output.customers().size());
        assertEquals(customer1.getName(), output.customers().get(0).name());
        assertEquals(customer2.getName(), output.customers().get(1).name());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void whenNoCustomers_thenShouldReturnEmptyList() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(List.of());
        
        // Act
        ListCustomerOutput output = listCustomerUseCase.execute(new UseCaseInput() {});
        
        // Assert
        assertNotNull(output);
        assertTrue(output.customers().isEmpty());
        verify(customerRepository, times(1)).findAll();
    }
}
