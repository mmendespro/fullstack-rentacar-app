package net.local.rentacar.application.usecases.rental;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.local.rentacar.application.dtos.ListActiveRentalOutput;
import net.local.rentacar.application.usecases.UseCaseInput;
import net.local.rentacar.domain.entities.Car;
import net.local.rentacar.domain.entities.Customer;
import net.local.rentacar.domain.entities.Rental;
import net.local.rentacar.domain.repositories.RentalRepository;
import net.local.rentacar.domain.vo.CarCategory;
import net.local.rentacar.domain.vo.CarStatus;
import net.local.rentacar.domain.vo.RentalStatus;

@ExtendWith(MockitoExtension.class)
class ListActiveRentalUseCaseTest {

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private ListActiveRentalUseCase listActiveRentalUseCase;

    @Test
    void whenListActiveRentals_thenShouldReturnListOfActiveRentals() {
        // Arrange
        Car car1 = new Car("ABC-1234", CarStatus.RENTED, CarCategory.ECONOMY);
        Customer customer1 = new Customer("John Doe", "123.456.789-00");
        Rental rental1 = new Rental(car1, customer1, LocalDateTime.now(), LocalDateTime.now().plusDays(3), new BigDecimal("300.00"));
        
        Car car2 = new Car("XYZ-9876", CarStatus.RENTED, CarCategory.LUXURY);
        Customer customer2 = new Customer("Jane Doe", "987.654.321-00");
        Rental rental2 = new Rental(car2, customer2, LocalDateTime.now(), LocalDateTime.now().plusDays(5), new BigDecimal("500.00"));
        
        List<Rental> activeRentals = Arrays.asList(rental1, rental2);
        
        when(rentalRepository.listRentalByStatus(RentalStatus.ACTIVE)).thenReturn(activeRentals);
        
        // Act
        ListActiveRentalOutput output = listActiveRentalUseCase.execute(new UseCaseInput() {});
        
        // Assert
        assertNotNull(output);
        assertEquals(2, output.activeRentals().size());
        verify(rentalRepository, times(1)).listRentalByStatus(RentalStatus.ACTIVE);
    }

    @Test
    void whenNoActiveRentals_thenShouldReturnEmptyList() {
        // Arrange
        when(rentalRepository.listRentalByStatus(RentalStatus.ACTIVE)).thenReturn(List.of());
        
        // Act
        ListActiveRentalOutput output = listActiveRentalUseCase.execute(new UseCaseInput() {});
        
        // Assert
        assertNotNull(output);
        assertTrue(output.activeRentals().isEmpty());
        verify(rentalRepository, times(1)).listRentalByStatus(RentalStatus.ACTIVE);
    }
}
