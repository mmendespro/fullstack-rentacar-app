package net.local.rentacar.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record RentalOutput(UUID id, String status, String customerName, LocalDateTime startDate, LocalDateTime expectedReturnDate) {}
