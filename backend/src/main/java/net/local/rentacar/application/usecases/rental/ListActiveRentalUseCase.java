package net.local.rentacar.application.usecases.rental;

import org.springframework.stereotype.Service;

import net.local.rentacar.application.dtos.ListActiveRentalOutput;
import net.local.rentacar.application.dtos.RentalOutput;
import net.local.rentacar.application.usecases.UseCase;
import net.local.rentacar.application.usecases.UseCaseInput;
import net.local.rentacar.domain.repositories.RentalRepository;
import net.local.rentacar.domain.vo.RentalStatus;

@Service
public class ListActiveRentalUseCase implements UseCase<UseCaseInput,ListActiveRentalOutput> {

    private final RentalRepository rentalRepository;

    public ListActiveRentalUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public ListActiveRentalOutput execute(UseCaseInput input) {
        var rentals = rentalRepository.listRentalByStatus(RentalStatus.ACTIVE)
                                      .stream()
                                      .map(r -> new RentalOutput(r.getId(), r.getStatus().name(), r.getCustomer().getName(), r.getStartDate(), r.getExpectedReturnDate()))
                                      .toList();
        return new ListActiveRentalOutput(rentals);
    }
    
}
