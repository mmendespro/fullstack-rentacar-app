package net.local.rentacar.application.dtos;

import net.local.rentacar.application.usecases.UseCaseOutput;

public record CreateCustomerOutput(String id) implements UseCaseOutput {}
