package net.local.rentacar.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import net.local.rentacar.application.usecases.UseCaseInput;

public record ReturnRentalInput(UUID rentalId, LocalDateTime actualReturnDate, int pointsToUse) implements UseCaseInput {}
