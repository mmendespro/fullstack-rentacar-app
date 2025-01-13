package net.local.rentacar.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import net.local.rentacar.domain.vo.RentalStatus;

public class Rental {
    
    private UUID id;
    private Customer customer;
    private Car car;
    private LocalDateTime startDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;
    private RentalStatus status;
    private BigDecimal basePrice;
    private BigDecimal finalPrice;
    private BigDecimal appliedDiscount;
    private Integer earnedPoints;
    
    private Rental(UUID id, Customer customer, Car car, LocalDateTime startDate, LocalDateTime expectedReturnDate, LocalDateTime actualReturnDate, RentalStatus status, BigDecimal basePrice, BigDecimal finalPrice, BigDecimal appliedDiscount, Integer earnedPoints) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
        this.appliedDiscount = appliedDiscount;
        this.earnedPoints = earnedPoints;
    }

    public Rental(Car car, Customer customer, LocalDateTime startDate, LocalDateTime expectedReturnDate, BigDecimal basePrice) {
        if(expectedReturnDate.isBefore(startDate)){
            throw new IllegalArgumentException("expectedReturnDate cannot be before startDate");
        }
        this.id = UUID.randomUUID();
        this.car = car;
        this.customer = customer;
        this.status = RentalStatus.ACTIVE;
        this.startDate = startDate;
        this.expectedReturnDate = expectedReturnDate;
        this.basePrice = basePrice;
    }

    public static Rental of(UUID id, Customer customer, Car car, LocalDateTime startDate, LocalDateTime expectedReturnDate, LocalDateTime actualReturnDate, RentalStatus status, BigDecimal basePrice, BigDecimal finalPrice, BigDecimal appliedDiscount, Integer earnedPoints) {
        return new Rental(id, customer, car, startDate, expectedReturnDate, actualReturnDate, status, basePrice, finalPrice, appliedDiscount, earnedPoints);
    }

    public void returnCar(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
        this.status = RentalStatus.COMPLETED;
    }

    public void changeFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void applyLoyaltyDiscount(int pointsToUse) {
        BigDecimal discount = LoyaltyProgram.calculateDiscount(pointsToUse);
        this.appliedDiscount = discount;
        this.customer.useLoyaltyPoints(pointsToUse);
    }
    
    public void calculateAndAddLoyaltyPoints() {
        this.earnedPoints = LoyaltyProgram.calculatePointsForRental(this);
        this.customer.addLoyaltyPoints(this.earnedPoints);
    }

    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public BigDecimal getAppliedDiscount() {
        return appliedDiscount;
    }

    public Integer getEarnedPoints() {
        return earnedPoints;
    }
}
