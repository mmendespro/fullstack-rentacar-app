package net.local.rentacar.domain.entities;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class LoyaltyProgram {
    
    private static final int POINTS_PER_DAY = 10;
    private static final int POINTS_FOR_ON_TIME_RETURN = 50;
    private static final BigDecimal DISCOUNT_PER_POINT = new BigDecimal("0.10"); // R$0,10 por ponto
    private static final int MINIMUM_POINTS_FOR_DISCOUNT = 100;
    
    public static int calculatePointsForRental(Rental rental) {
        long rentalDays = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getExpectedReturnDate());
        int points = (int) (rentalDays * POINTS_PER_DAY);
        
        // Bônus por devolução no prazo
        if (rental.getActualReturnDate() != null && 
            !rental.getActualReturnDate().isAfter(rental.getExpectedReturnDate())) {
            points += POINTS_FOR_ON_TIME_RETURN;
        }
        
        return points;
    }
    
    public static BigDecimal calculateDiscount(int points) {
        if (points < MINIMUM_POINTS_FOR_DISCOUNT) {
            return BigDecimal.ZERO;
        }
        return DISCOUNT_PER_POINT.multiply(BigDecimal.valueOf(points));
    }
}