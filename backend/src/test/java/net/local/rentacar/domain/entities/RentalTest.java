package net.local.rentacar.domain.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import net.local.rentacar.domain.vo.CarCategory;
import net.local.rentacar.domain.vo.CarStatus;
import net.local.rentacar.domain.vo.RentalStatus;

class RentalTest {

    @Test
    void whenCreateRental_thenShouldInitializeWithCorrectValues() {
        // Arrange
        Car car = Car.of(UUID.randomUUID(), "ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        Customer customer = new Customer("John Doe", "123.456.789-00");
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        BigDecimal basePrice = new BigDecimal("300.00");
        
        // Act
        Rental rental = new Rental(car, customer, startDate, expectedReturnDate, basePrice);
        
        // Assert
        assertNotNull(rental.getId());
        assertEquals(car, rental.getCar());
        assertEquals(customer, rental.getCustomer());
        assertEquals(startDate, rental.getStartDate());
        assertEquals(expectedReturnDate, rental.getExpectedReturnDate());
        assertEquals(RentalStatus.ACTIVE, rental.getStatus());
        assertNull(rental.getActualReturnDate());
        assertEquals(basePrice, rental.getBasePrice());
    }

    @Test
    void whenReturnRental_thenShouldUpdateStatusAndReturnDate() {
        // Arrange
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        Customer customer = new Customer("John Doe", "123.456.789-00");
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        BigDecimal basePrice = new BigDecimal("300.00");
        Rental rental = new Rental(car, customer, startDate, expectedReturnDate, basePrice);
        
        LocalDateTime returnDate = LocalDateTime.now().plusDays(2);
        
        // Act
        rental.returnCar(returnDate);
        
        // Assert
        assertEquals(RentalStatus.COMPLETED, rental.getStatus());
        assertEquals(returnDate, rental.getActualReturnDate());
    }

    @Test
    void whenCreateRentalWithInvalidDates_thenShouldThrowException() {
        // Arrange
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        Customer customer = new Customer("John Doe", "123.456.789-00");
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime invalidReturnDate = startDate.minusDays(1);
        BigDecimal basePrice = new BigDecimal("300.00");
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Rental(car, customer, startDate, invalidReturnDate, basePrice);
        });
    }

    @Test
    void whenCreateRentalWithStaticFactoryMethod_thenShouldCreateRentalWithGivenId() {
        // Arrange
        UUID expectedId = UUID.randomUUID();
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        Customer customer = new Customer("John Doe", "123.456.789-00");
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        LocalDateTime actualReturnDate = null;
        BigDecimal basePrice = new BigDecimal("300.00");
        BigDecimal finalPrice = null;
        BigDecimal appliedDiscount = null;
        Integer earnedPoints = null;
        
        // Act
        Rental rental = Rental.of(
            expectedId,
            customer,
            car,
            startDate,
            expectedReturnDate,
            actualReturnDate,
            RentalStatus.ACTIVE,
            basePrice,
            finalPrice,
            appliedDiscount,
            earnedPoints
        );
        
        // Assert
        assertEquals(expectedId, rental.getId());
        assertEquals(car, rental.getCar());
        assertEquals(customer, rental.getCustomer());
        assertEquals(startDate, rental.getStartDate());
        assertEquals(expectedReturnDate, rental.getExpectedReturnDate());
        assertEquals(RentalStatus.ACTIVE, rental.getStatus());
        assertNull(rental.getActualReturnDate());
        assertEquals(basePrice, rental.getBasePrice());
    }

    @Test
    void whenApplyLoyaltyDiscount_thenShouldUpdateDiscountAndCustomerPoints() {
        // Arrange
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        Customer customer = new Customer("John Doe", "123.456.789-00");
        customer.addLoyaltyPoints(500);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        BigDecimal basePrice = new BigDecimal("300.00");
        Rental rental = new Rental(car, customer, startDate, expectedReturnDate, basePrice);
        
        int pointsToUse = 100;
        
        // Act
        rental.applyLoyaltyDiscount(pointsToUse);
        
        // Assert
        assertNotNull(rental.getAppliedDiscount());
        assertEquals(400, customer.getLoyaltyPoints());
    }

    @Test
    void whenCalculateAndAddLoyaltyPoints_thenShouldUpdateEarnedPoints() {
        // Arrange
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        Customer customer = new Customer("John Doe", "123.456.789-00");
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expectedReturnDate = startDate.plusDays(3);
        BigDecimal basePrice = new BigDecimal("300.00");
        Rental rental = new Rental(car, customer, startDate, expectedReturnDate, basePrice);
        
        // Act
        rental.calculateAndAddLoyaltyPoints();
        
        // Assert
        assertNotNull(rental.getEarnedPoints());
        assertTrue(rental.getEarnedPoints() > 0);
        assertEquals(rental.getEarnedPoints(), customer.getLoyaltyPoints());
    }
}
