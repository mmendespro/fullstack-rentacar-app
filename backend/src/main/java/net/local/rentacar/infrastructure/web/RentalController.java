package net.local.rentacar.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.local.rentacar.application.dtos.CreateRentalInput;
import net.local.rentacar.application.dtos.CreateRentalOutput;
import net.local.rentacar.application.dtos.ListActiveRentalOutput;
import net.local.rentacar.application.dtos.ReturnRentalInput;
import net.local.rentacar.application.dtos.ReturnRentalOutput;
import net.local.rentacar.application.usecases.rental.CreateRentalUseCase;
import net.local.rentacar.application.usecases.rental.ListActiveRentalUseCase;
import net.local.rentacar.application.usecases.rental.ReturnCarUseCase;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rentals", description = "Endpoints for managing car rentals")
public class RentalController {
    
    private final ReturnCarUseCase returnCarUseCase;
    private final CreateRentalUseCase createRentalUseCase;
    private final ListActiveRentalUseCase listActiveRental;

    public RentalController(ReturnCarUseCase returnCarUseCase, CreateRentalUseCase createRentalUseCase, ListActiveRentalUseCase listActiveRental) {
        this.returnCarUseCase = returnCarUseCase;
        this.createRentalUseCase = createRentalUseCase;
        this.listActiveRental = listActiveRental;
    }

    @GetMapping
    @Operation(summary = "List active rentals", description = "List all active car rentals")
    @ApiResponse(responseCode = "200", description = "Rental list successfully", content = @Content(schema = @Schema(implementation = ListActiveRentalOutput.class)))
    public ResponseEntity<ListActiveRentalOutput> listActiveRental() {
        var result = listActiveRental.execute(null);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(summary = "Create a new rental", description = "Creates a new car rental for a customer")
    @ApiResponse(responseCode = "200", description = "Rental created successfully", content = @Content(schema = @Schema(implementation = CreateRentalOutput.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<CreateRentalOutput> createRental(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Rental creation details", required = true, content = @Content(schema = @Schema(implementation = CreateRentalInput.class)))
            @RequestBody CreateRentalInput request) {
        var result = createRentalUseCase.execute(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/return")
    @Operation(summary = "Return a rented car", description = "Processes the return of a rented car and calculates final charges")
    @ApiResponse(responseCode = "200", description = "Car returned successfully", content = @Content(schema = @Schema(implementation = ReturnRentalOutput.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Rental not found")
    public ResponseEntity<ReturnRentalOutput> returnCar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Car return details", required = true, content = @Content(schema = @Schema(implementation = ReturnRentalInput.class)))
            @RequestBody ReturnRentalInput request) {
        var result = returnCarUseCase.execute(request);
        return ResponseEntity.ok(result);
    }
}
