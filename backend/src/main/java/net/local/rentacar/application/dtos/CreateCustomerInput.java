package net.local.rentacar.application.dtos;

import jakarta.validation.constraints.NotBlank;
import net.local.rentacar.application.usecases.UseCaseInput;
import net.local.rentacar.application.validation.SelfValidating;

public class CreateCustomerInput extends SelfValidating<CreateCustomerInput> implements UseCaseInput {
    
    @NotBlank(message = "[name] field is required")
    private final String name;

    @NotBlank(message = "[document] field is required")
    private final String document;

    public CreateCustomerInput(String name, String document) {
        this.name = name;
        this.document = document;
        this.selfValidate(this);
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }
}