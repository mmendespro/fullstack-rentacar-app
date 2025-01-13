package net.local.rentacar.domain.strategy;

import java.math.BigDecimal;

import net.local.rentacar.domain.entities.Rental;

public interface PriceCalculationStrategy {
    BigDecimal calculatePrice(Rental rental);
}
