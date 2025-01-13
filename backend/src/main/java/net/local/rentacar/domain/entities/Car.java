package net.local.rentacar.domain.entities;

import java.util.UUID;

import net.local.rentacar.domain.vo.CarCategory;
import net.local.rentacar.domain.vo.CarStatus;

public class Car {
    
    private UUID id;
    private String plate;
    private CarStatus status;
    private CarCategory category;
    
    public Car(String plate, CarCategory category) {
        this.id = UUID.randomUUID();
        this.plate = plate;
        this.status = CarStatus.AVAILABLE;
        this.category = category;
    }

    private Car(UUID id, String plate, CarStatus status, CarCategory category) {
        this.id = id;
        this.plate = plate;
        this.status = status;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public CarStatus getStatus() {
        return status;
    }

    public CarCategory getCategory() {
        return category;
    }

    public Car rent() {
        return new Car(id, plate, CarStatus.RENTED, category);
    }

    public Car markAsAvailable() {
        return new Car(id, plate, CarStatus.AVAILABLE, category);
    }

    public static Car of(UUID id, String plate, CarStatus status, CarCategory category) {
        return new Car(id, plate, status, category);
    }
}
