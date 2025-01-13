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
import net.local.rentacar.application.dtos.CreateCustomerInput;
import net.local.rentacar.application.dtos.CreateCustomerOutput;
import net.local.rentacar.application.dtos.ListCustomerOutput;
import net.local.rentacar.application.usecases.customer.CreateCustomerUseCase;
import net.local.rentacar.application.usecases.customer.ListCustomerUseCase;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Endpoints for managing Customers")
public class CustomerController {
    
    private final CreateCustomerUseCase createCustomerUseCase;
    private final ListCustomerUseCase listCustomerUseCase;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, ListCustomerUseCase listCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.listCustomerUseCase = listCustomerUseCase;
    }

    @GetMapping
    @Operation(summary = "List Customers", description = "List all Customers")
    @ApiResponse(responseCode = "200", description = "Customer list successfully", content = @Content(schema = @Schema(implementation = ListCustomerOutput.class)))
    public ResponseEntity<ListCustomerOutput> loadAllCustomers() {
        var result = listCustomerUseCase.execute(null);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates a new customer")
    @ApiResponse(responseCode = "200", description = "Customer created successfully", content = @Content(schema = @Schema(implementation = CreateCustomerOutput.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<CreateCustomerOutput> createCustomer(@RequestBody CreateCustomerInput input) {
        var result = createCustomerUseCase.execute(input);
        return ResponseEntity.ok(result);
    }
}
