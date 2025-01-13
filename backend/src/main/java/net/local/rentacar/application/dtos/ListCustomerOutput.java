package net.local.rentacar.application.dtos;

import java.util.List;

import net.local.rentacar.application.usecases.UseCaseOutput;

public record ListCustomerOutput(List<CustomerOutput> customers) implements UseCaseOutput {}
