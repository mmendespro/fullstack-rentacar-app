package net.local.rentacar.domain.strategy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.local.rentacar.domain.entities.Rental;

class PriceCalculationFactoryTest {

    @InjectMocks
    private PriceCalculationFactory priceCalculationFactory;

    @Mock
    private Rental rental;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculate_shouldReturnStandardPrice_whenCategoryIsStandard() {
        when(rental.getBasePrice()).thenReturn(BigDecimal.TEN);
        when(rental.getStartDate()).thenReturn(LocalDateTime.now());
        when(rental.getExpectedReturnDate()).thenReturn(LocalDateTime.now().plusDays(3));
        when(rental.getActualReturnDate()).thenReturn(LocalDateTime.now().plusDays(3));
        
        BigDecimal result = priceCalculationFactory.calculate("STANDARD", rental);
        
        assertNotNull(result);
    }

    @Test
    void calculate_shouldReturnPremiumPrice_whenCategoryIsPremium() {
        when(rental.getBasePrice()).thenReturn(BigDecimal.TEN);
        when(rental.getStartDate()).thenReturn(LocalDateTime.now());
        when(rental.getExpectedReturnDate()).thenReturn(LocalDateTime.now().plusDays(3));
        when(rental.getActualReturnDate()).thenReturn(LocalDateTime.now().plusDays(3));
        
        BigDecimal result = priceCalculationFactory.calculate("PREMIUM", rental);
        
        assertNotNull(result);
    }

    @Test
    void calculate_shouldThrowException_whenCategoryIsInvalid() {
        when(rental.getStartDate()).thenReturn(LocalDateTime.now());
        when(rental.getExpectedReturnDate()).thenReturn(LocalDateTime.now().plusDays(3));
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            priceCalculationFactory.calculate("INVALID", rental);
        });
        
        assertEquals("No creator found for record type: INVALID", exception.getMessage());
    }
}
