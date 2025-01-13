package net.local.rentacar.application.dtos;

import java.util.List;

import net.local.rentacar.application.usecases.UseCaseOutput;

public record ListActiveRentalOutput(List<RentalOutput> activeRentals) implements UseCaseOutput{}
