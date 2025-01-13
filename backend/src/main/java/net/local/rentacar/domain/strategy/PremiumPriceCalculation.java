package net.local.rentacar.domain.strategy;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import net.local.rentacar.domain.entities.Rental;

public class PremiumPriceCalculation implements PriceCalculationStrategy {
    
    private static final BigDecimal LATE_FEE_PERCENTAGE = new BigDecimal("0.15"); // 15% ao dia
    private static final BigDecimal PREMIUM_DISCOUNT = new BigDecimal("0.05"); // 5% de desconto

    @Override
    public BigDecimal calculatePrice(Rental rental) {
        
        BigDecimal basePrice = rental.getBasePrice();
        BigDecimal discountedPrice = basePrice.multiply(BigDecimal.ONE.subtract(PREMIUM_DISCOUNT));
        
        if (rental.getActualReturnDate().isAfter(rental.getExpectedReturnDate())) {
            long daysLate = ChronoUnit.DAYS.between(
                rental.getExpectedReturnDate(), 
                rental.getActualReturnDate()
            );
            
            BigDecimal lateFee = basePrice.multiply(LATE_FEE_PERCENTAGE).multiply(BigDecimal.valueOf(daysLate));
            
            return discountedPrice.add(lateFee);
        }
        
        return discountedPrice;
    }
}