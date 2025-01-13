package net.local.rentacar.domain.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import net.local.rentacar.domain.vo.CarCategory;
import net.local.rentacar.domain.vo.CarStatus;

import java.util.UUID;

class CarTest {

    @Test
    void whenCreateCar_thenShouldInitializeWithCorrectValues() {
        // Arrange
        UUID id = UUID.randomUUID();
        String plate = "ABC-1234";
        CarStatus status = CarStatus.AVAILABLE;
        CarCategory category = CarCategory.ECONOMY;
        
        // Act
        Car car = Car.of(id, plate, status, category);
        
        // Assert
        assertNotNull(car.getId());
        assertEquals(plate, car.getPlate());
        assertEquals(status, car.getStatus());
        assertEquals(category, car.getCategory());
    }

    @Test
    void whenChangeStatus_thenShouldReturnNewCarWithUpdatedStatus() {
        // Arrange
        Car car = Car.of(UUID.randomUUID(),"ABC-1234", CarStatus.AVAILABLE, CarCategory.ECONOMY);
        
        // Act
        Car updatedCar = car.rent();
        
        // Assert
        assertEquals(CarStatus.RENTED, updatedCar.getStatus());
        assertEquals(car.getId(), updatedCar.getId());
        assertEquals(car.getPlate(), updatedCar.getPlate());
        assertEquals(car.getCategory(), updatedCar.getCategory());
    }

    @Test
    void whenCreateCarWithStaticFactoryMethod_thenShouldCreateCarWithGivenId() {
        // Arrange
        UUID expectedId = UUID.randomUUID();
        String plate = "ABC-1234";
        CarStatus status = CarStatus.AVAILABLE;
        CarCategory category = CarCategory.ECONOMY;
        
        // Act
        Car car = Car.of(expectedId, plate, status, category);
        
        // Assert
        assertEquals(expectedId, car.getId());
        assertEquals(plate, car.getPlate());
        assertEquals(status, car.getStatus());
        assertEquals(category, car.getCategory());
    }
}
