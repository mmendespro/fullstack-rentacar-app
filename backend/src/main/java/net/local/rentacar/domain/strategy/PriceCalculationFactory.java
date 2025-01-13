package net.local.rentacar.domain.strategy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import net.local.rentacar.domain.entities.Rental;

@Component
public class PriceCalculationFactory {
    
    private final Map<String, PriceCalculationStrategy> strategies;

    public PriceCalculationFactory() {
        this.strategies = new HashMap<>();
        this.strategies.put("STANDARD", new StandardPriceCalculation());
        this.strategies.put("PREMIUM", new PremiumPriceCalculation());
    }

    public BigDecimal calculate(String category, Rental rental) {
        PriceCalculationStrategy strategy = strategies.get(category);
        if (strategy == null) {
            throw new IllegalArgumentException("No creator found for record type: " + category);
        }
        return strategy.calculatePrice(rental);
    }
}
