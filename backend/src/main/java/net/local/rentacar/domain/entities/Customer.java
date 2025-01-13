package net.local.rentacar.domain.entities;

import java.util.UUID;

public class Customer {
    
    private final int MINIMUM_POINTS_PREMIUM = 1000;

    private UUID id;
    private String name;
    private String document;
    private Integer loyaltyPoints;
    
    public Customer(String name, String document) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.document = document;
        this.loyaltyPoints = 0;
    }

    private Customer(UUID id, String name, String document, Integer loyaltyPoints) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.loyaltyPoints = loyaltyPoints;
    }

    public static Customer of(UUID id, String name, String document, Integer loyaltyPoints) {
        return new Customer(id, name, document, loyaltyPoints);
    }

    public void addLoyaltyPoints(Integer points) {
        this.loyaltyPoints += points;
    }
    
    public void useLoyaltyPoints(Integer points) {
        if (this.loyaltyPoints >= points) {
            this.loyaltyPoints -= points;
        } else {
            throw new IllegalArgumentException("Insufficient loyalty points");
        }
    }
        
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public String getCategory() {
        if(loyaltyPoints == null) {
            return "STANDARD";
        }
        return (loyaltyPoints >= MINIMUM_POINTS_PREMIUM) ? "PREMIUM" : "STANDARD";
    }
}
