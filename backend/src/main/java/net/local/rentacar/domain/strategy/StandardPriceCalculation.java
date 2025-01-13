package net.local.rentacar.domain.strategy;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import net.local.rentacar.domain.entities.Rental;

public class StandardPriceCalculation implements PriceCalculationStrategy {
    
    private static final BigDecimal LATE_FEE_PERCENTAGE = new BigDecimal("0.1"); // 10% ao dia

    @Override
    public BigDecimal calculatePrice(Rental rental) {
        BigDecimal basePrice = rental.getBasePrice();
        
        if (rental.getActualReturnDate().isAfter(rental.getExpectedReturnDate())) {
            long daysLate = ChronoUnit.DAYS.between(
                rental.getExpectedReturnDate(), 
                rental.getActualReturnDate()
            );
            
            BigDecimal lateFee = basePrice.multiply(LATE_FEE_PERCENTAGE).multiply(BigDecimal.valueOf(daysLate));
            
            return basePrice.add(lateFee);
        }
        
        return basePrice;
    }
}
