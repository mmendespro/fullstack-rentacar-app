package net.local.rentacar.application.usecases.rental;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import net.local.rentacar.application.dtos.ReturnRentalInput;
import net.local.rentacar.application.dtos.ReturnRentalOutput;
import net.local.rentacar.application.usecases.UseCase;
import net.local.rentacar.domain.entities.Rental;
import net.local.rentacar.domain.exceptions.RentalNotFoundException;
import net.local.rentacar.domain.repositories.CarRepository;
import net.local.rentacar.domain.repositories.CustomerRepository;
import net.local.rentacar.domain.repositories.RentalRepository;
import net.local.rentacar.domain.strategy.PriceCalculationFactory;
import net.local.rentacar.domain.vo.CarStatus;

@Service
public class ReturnCarUseCase implements UseCase<ReturnRentalInput,ReturnRentalOutput>{
    
    private final CarRepository carRepository;
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final PriceCalculationFactory priceCalculationFactory;

    public ReturnCarUseCase(CarRepository carRepository, RentalRepository rentalRepository, CustomerRepository customerRepository, PriceCalculationFactory priceCalculationFactory) {
        this.carRepository = carRepository;
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.priceCalculationFactory = priceCalculationFactory;
    }

    @Override
    public ReturnRentalOutput execute(ReturnRentalInput input) {
        Rental rental = rentalRepository.findById(input.rentalId()).orElseThrow(() -> new RentalNotFoundException(input.rentalId().toString()));

        rental.returnCar(input.actualReturnDate());
        
        BigDecimal finalPrice = priceCalculationFactory.calculate(rental.getCustomer().getCategory(), rental);
        rental.changeFinalPrice(finalPrice);

        // Aplica desconto por pontuação de fidelidade
        rental.applyLoyaltyDiscount(input.pointsToUse());

        // Calcula e adiciona pontos de fidelidade
        rental.calculateAndAddLoyaltyPoints();

        // Salva modificações no aluguel
        rentalRepository.save(rental);

        // Salva modificações no customer devido alterações na pontuação
        customerRepository.save(rental.getCustomer());

        // Salva modificações no Car devido alterações no status
        carRepository.save(rental.getCar().changeStatus(CarStatus.AVAILABLE));
        
        return new ReturnRentalOutput(rental.getId(), rental.getFinalPrice(), rental.getActualReturnDate());
    }
}
