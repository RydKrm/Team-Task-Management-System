package com.TaskManagement.controller.company.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateCompanyDto {
    @NotNull(message = "Company name is required")
    @Length(max = 30, min = 3, message = "Company name greater than 3 and lower than 30")
    private String name;

    @NotNull(message = "Description is required")
    private String description;

}
