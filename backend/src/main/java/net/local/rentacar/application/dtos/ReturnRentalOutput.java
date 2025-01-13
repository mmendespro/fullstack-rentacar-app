package net.local.rentacar.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import net.local.rentacar.application.usecases.UseCaseOutput;

public record ReturnRentalOutput(UUID rentalId, BigDecimal finalPrice, LocalDateTime returnDate) implements UseCaseOutput {}