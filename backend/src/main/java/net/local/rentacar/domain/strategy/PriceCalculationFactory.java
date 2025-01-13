package net.local.rentacar.domain.strategy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import net.local.rentacar.domain.entities.Rental;

/**
 * Implementa o padrão Strategy combinado com Factory Method para cálculo de preços de aluguel.
 * 
 * <p>Esta classe atua como uma fábrica centralizada que encapsula diferentes estratégias
 * de cálculo de preço (StandardPriceCalculation e PremiumPriceCalculation) em um Map,
 * permitindo a seleção dinâmica da estratégia apropriada com base na categoria do cliente.</p>
 * 
 * <p>Benefícios desta abordagem:
 * <ul>
 *   <li>Facilita a adição de novas estratégias de cálculo sem modificar o código existente</li>
 *   <li>Centraliza a lógica de seleção de estratégias em um único ponto</li>
 *   <li>Promove o baixo acoplamento entre as estratégias e o código cliente</li>
 *   <li>Permite fácil teste e manutenção das estratégias individuais</li>
 * </ul>
 * </p>
 */
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
            throw new IllegalArgumentException("No calculator found for category: " + category);
        }
        return strategy.calculatePrice(rental);
    }
}
