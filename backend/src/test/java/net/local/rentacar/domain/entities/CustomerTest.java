package net.local.rentacar.domain.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.UUID;

class CustomerTest {

    @Test
    void whenCreateCustomer_thenShouldInitializeWithCorrectValues() {
        // Arrange
        String name = "John Doe";
        String document = "123.456.789-00";
        
        // Act
        Customer customer = new Customer(name, document);
        
        // Assert
        assertNotNull(customer.getId());
        assertEquals(name, customer.getName());
        assertEquals(document, customer.getDocument());
        assertEquals(0, customer.getLoyaltyPoints());
    }

    @Test
    void whenAddLoyaltyPoints_thenShouldIncreasePoints() {
        // Arrange
        Customer customer = new Customer("John Doe", "123.456.789-00");
        int pointsToAdd = 100;
        
        // Act
        customer.addLoyaltyPoints(pointsToAdd);
        
        // Assert
        assertEquals(pointsToAdd, customer.getLoyaltyPoints());
    }

    @Test
    void whenCreateCustomerWithStaticFactoryMethod_thenShouldCreateCustomerWithGivenId() {
        // Arrange
        UUID expectedId = UUID.randomUUID();
        String name = "John Doe";
        String document = "123.456.789-00";
        Integer loyaltyPoints = 100;
        
        // Act
        Customer customer = Customer.of(expectedId, name, document, loyaltyPoints);
        
        // Assert
        assertEquals(expectedId, customer.getId());
        assertEquals(name, customer.getName());
        assertEquals(document, customer.getDocument());
        assertEquals(loyaltyPoints, customer.getLoyaltyPoints());
    }

    @Test
    void whenUseLoyaltyPoints_thenShouldDecreasePoints() {
        // Arrange
        Customer customer = new Customer("John Doe", "123.456.789-00");
        customer.addLoyaltyPoints(200);
        
        // Act
        customer.useLoyaltyPoints(100);
        
        // Assert
        assertEquals(100, customer.getLoyaltyPoints());
    }

    @Test
    void whenUseMoreLoyaltyPointsThanAvailable_thenShouldThrowException() {
        // Arrange
        Customer customer = new Customer("John Doe", "123.456.789-00");
        customer.addLoyaltyPoints(50);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            customer.useLoyaltyPoints(100);
        });
    }

    @Test
    void whenGetCategoryWithEnoughPoints_thenShouldReturnPremium() {
        // Arrange
        Customer customer = new Customer("John Doe", "123.456.789-00");
        customer.addLoyaltyPoints(1000);
        
        // Act & Assert
        assertEquals("PREMIUM", customer.getCategory());
    }

    @Test
    void whenGetCategoryWithoutEnoughPoints_thenShouldReturnStandard() {
        // Arrange
        Customer customer = new Customer("John Doe", "123.456.789-00");
        customer.addLoyaltyPoints(500);
        
        // Act & Assert
        assertEquals("STANDARD", customer.getCategory());
    }
}
