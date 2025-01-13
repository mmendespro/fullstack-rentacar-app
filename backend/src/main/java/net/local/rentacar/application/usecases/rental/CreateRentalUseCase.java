package net.local.rentacar.application.usecases.rental;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import net.local.rentacar.application.dtos.CreateRentalInput;
import net.local.rentacar.application.dtos.CreateRentalOutput;
import net.local.rentacar.application.usecases.UseCase;
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
import net.local.rentacar.domain.vo.CarStatus;

@Service
public class CreateRentalUseCase implements UseCase<CreateRentalInput,CreateRentalOutput> {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;
    private final HolidayService holidayService;

    private static final BigDecimal WEEKEND_RATE_MULTIPLIER = new BigDecimal("1.2");  // 20% mais caro
    private static final BigDecimal HOLIDAY_RATE_MULTIPLIER = new BigDecimal("1.3");  // 30% mais caro
    
    public CreateRentalUseCase(CarRepository carRepository, CustomerRepository customerRepository, RentalRepository rentalRepository, HolidayService holidayService) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
        this.holidayService = holidayService;
    }

    @Override
    public CreateRentalOutput execute(CreateRentalInput input) {
        Car car = carRepository.findById(input.carId()).orElseThrow(() -> new CarNotFountException(input.carId().toString()));

        if(car.getStatus().equals(CarStatus.RENTED)) {
            throw new CarNotAvailableException(input.carId().toString());
        }
        
        Customer customer = customerRepository.findById(input.customerId()).orElseThrow(() -> new CustomerNotFoundException(input.customerId().toString()));

        BigDecimal basePrice = calculateBasePrice(car, input.startDate(), input.expectedReturnDate());
        
        Rental rental = new Rental(car.changeStatus(CarStatus.RENTED), customer, input.startDate(), input.expectedReturnDate(), basePrice);
        rentalRepository.save(rental);
        carRepository.save(rental.getCar());

        return new CreateRentalOutput(rental.getId(), basePrice, rental.getStartDate(), rental.getExpectedReturnDate());
    }

    private BigDecimal calculateBasePrice(Car car, LocalDateTime startDate, LocalDateTime expectedReturnDate) {
     
        BigDecimal baseRate = car.getCategory().getDailyRate();
        BigDecimal totalPrice = BigDecimal.ZERO;
        
        LocalDateTime currentDate = startDate;
        while (currentDate.isBefore(expectedReturnDate)) {
            BigDecimal dailyRate = baseRate;
            
            // Verifica se é fim de semana (sábado ou domingo)
            if (isWeekend(currentDate)) {
                dailyRate = dailyRate.multiply(WEEKEND_RATE_MULTIPLIER);
            }
            
            // Verifica se é feriado
            if (holidayService.isHoliday(currentDate.toLocalDate())) {
                dailyRate = dailyRate.multiply(HOLIDAY_RATE_MULTIPLIER);
            }
            
            totalPrice = totalPrice.add(dailyRate);
            currentDate = currentDate.plusDays(1);
        }
        
        return totalPrice;
    }

    private boolean isWeekend(LocalDateTime date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
