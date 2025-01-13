package net.local.rentacar.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import net.local.rentacar.application.usecases.UseCaseOutput;

public record CreateRentalOutput(UUID rentalId, BigDecimal basePrice, LocalDateTime startDate, LocalDateTime expectedReturnDate) implements UseCaseOutput {}
