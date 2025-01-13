package net.local.rentacar.application.usecases.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.local.rentacar.application.dtos.ReturnRentalInput;
import net.local.rentacar.application.dtos.ReturnRentalOutput;
import net.local.rentacar.domain.entities.Car;
import net.local.rentacar.domain.entities.Customer;
import net.local.rentacar.domain.entities.Rental;
import net.local.rentacar.domain.exceptions.RentalNotFoundException;
import net.local.rentacar.domain.repositories.CarRepository;
import net.local.rentacar.domain.repositories.CustomerRepository;
import net.local.rentacar.domain.repositories.RentalRepository;
import net.local.rentacar.domain.services.HolidayService;
import net.local.rentacar.domain.strategy.PriceCalculationFactory;
import net.local.rentacar.domain.strategy.PriceCalculationStrategy;
import net.local.rentacar.domain.vo.CarCategory;
import net.local.rentacar.domain.vo.CarStatus;
import net.local.rentacar.domain.vo.RentalStatus;

@ExtendWith(MockitoExtension.class)
class ReturnCarUseCaseTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private HolidayService holidayService;

    @Mock
    private PriceCalculationFactory priceCalculationFactory;

    @Mock
    private PriceCalculationStrategy priceCalculationStrategy;
    
    @InjectMocks
    private ReturnCarUseCase returnCarUseCase;

    @Test
    void whenReturnCar_thenShouldUpdateRentalStatus() {
        // Arrange
        UUID rentalId = UUID.randomUUID();
        LocalDateTime actualReturnDate = LocalDateTime.now();
        int pointsToUse = 0;
        
        Car car = new Car("ABC-1234", CarStatus.RENTED, CarCategory.ECONOMY);
        Customer customer = new Customer("John Doe", "123.456.789-00");
        Rental rental = new Rental(car, customer, LocalDateTime.now(), LocalDateTime.now().plusDays(3), new BigDecimal("300.00"));
        
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        
        ReturnRentalInput input = new ReturnRentalInput(rentalId, actualReturnDate, pointsToUse);
        
        // Act
        ReturnRentalOutput output = returnCarUseCase.execute(input);
        
        // Assert
        assertNotNull(output);
        assertEquals(RentalStatus.COMPLETED, rental.getStatus());
        verify(rentalRepository, times(1)).save(rental);
    }

    @Test
    void whenReturnCarWithInvalidRental_thenShouldThrowException() {
        // Arrange
        UUID rentalId = UUID.randomUUID();
        LocalDateTime actualReturnDate = LocalDateTime.now();
        int pointsToUse = 0;
        
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.empty());
        
        ReturnRentalInput input = new ReturnRentalInput(rentalId, actualReturnDate, pointsToUse);
        
        // Act & Assert
        assertThrows(RentalNotFoundException.class, () -> {
            returnCarUseCase.execute(input);
        });
    }
}
