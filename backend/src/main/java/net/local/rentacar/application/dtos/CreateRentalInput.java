package net.local.rentacar.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import net.local.rentacar.application.usecases.UseCaseInput;

public record CreateRentalInput(UUID carId, UUID customerId, LocalDateTime startDate, LocalDateTime expectedReturnDate) implements UseCaseInput {}

