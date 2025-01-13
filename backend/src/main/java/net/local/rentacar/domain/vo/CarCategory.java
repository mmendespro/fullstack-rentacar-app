package net.local.rentacar.domain.vo;

import java.math.BigDecimal;

public enum CarCategory {
    
    ECONOMY(new BigDecimal("100.00")),      // R$ 100/dia
    INTERMEDIATE(new BigDecimal("150.00")),  // R$ 150/dia
    LUXURY(new BigDecimal("300.00")),       // R$ 300/dia
    SUV(new BigDecimal("200.00")),          // R$ 200/dia
    PREMIUM(new BigDecimal("400.00"));       // R$ 400/dia

    private final BigDecimal dailyRate;

    CarCategory(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }
}