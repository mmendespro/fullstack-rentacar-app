package net.local.rentacar.application.usecases.rental;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.local.rentacar.application.dtos.CreateRentalInput;
import net.local.rentacar.application.dtos.CreateRentalOutput;
import net.local.rentacar.domain.entities.Car;
import net.local.rentacar.domain.entities.Customer;
import net.local.rentacar.domain.entities.Rental;
import net.local.rentacar.domain.exceptions.CarNotAvailableException;
import net.local.rentacar.domain.exceptions.CarNotFountException;
import net.local.rentacar.domain.exceptions.CustomerNotFoundException;
import net.local.rentacar.domain.repositories.CarRepository;
import net.local.rentacar.domain.repositories.CustomerRepository;
import net.local.rentacar.domain.repositories.RentalRepository;
import net.local.rentacar.domain.services.HolidayService;
import net.local.rentacar.domain.strategy.PriceCalculationStrategy;
import net.local.rentacar.domain.vo.CarCategory;
import net.local.rentacar.domain.vo.CarStatus;

@ExtendWith(MockitoExtension.class)
class CreateRentalUseCaseTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private HolidayService holidayService;

    @Mock
    private PriceCalculationStrategy priceCalculationStrategy;

    @InjectMocks
    private CreateRentalUseCase createRentalUseCase;

    @Test
    void whenCreateRental_thenShouldReturnRentalOutput() {
        // Arrange
        UUID carId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        Customer customer = new Customer("John Doe", "123.456.789-00");
        
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        CreateRentalInput input = new CreateRentalInput(carId, customerId, startDate, expectedReturnDate);
        
        // Act
        CreateRentalOutput output = createRentalUseCase.execute(input);
        
        // Assert
        assertNotNull(output);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void whenCreateRentalWithInvalidCar_thenShouldThrowException() {
        // Arrange
        UUID carId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        
        when(carRepository.findById(carId)).thenReturn(Optional.empty());
        
        CreateRentalInput input = new CreateRentalInput(carId, customerId, startDate, expectedReturnDate);
        
        // Act & Assert
        assertThrows(CarNotFountException.class, () -> {
            createRentalUseCase.execute(input);
        });
    }

    @Test
    void whenCreateRentalWithInvalidCustomer_thenShouldThrowException() {
        // Arrange
        UUID carId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        
        CreateRentalInput input = new CreateRentalInput(carId, customerId, startDate, expectedReturnDate);
        
        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> {
            createRentalUseCase.execute(input);
        });
    }

    @Test
    void whenCreateRentalWithUnavailableCar_thenShouldThrowException() {
        // Arrange
        UUID carId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.RENTED, CarCategory.ECONOMY);
        
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        
        CreateRentalInput input = new CreateRentalInput(carId, customerId, startDate, expectedReturnDate);
        
        // Act & Assert
        assertThrows(CarNotAvailableException.class, () -> {
            createRentalUseCase.execute(input);
        });
    }
}
