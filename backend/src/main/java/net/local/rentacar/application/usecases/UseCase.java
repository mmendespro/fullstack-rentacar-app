package net.local.rentacar.application.usecases;

public interface UseCase<I extends UseCaseInput, O extends UseCaseOutput> {
    O execute(I input);
}
