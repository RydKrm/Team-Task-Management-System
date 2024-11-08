package com.TaskManagement.controller.team.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateTeamDto {
    @NotNull(message = "Team name is required")
    @Length(max = 100, min = 6, message = "Team name greater than 6 and lower than 30")
    private String name;

    @NotNull(message = "Team service is required")
    @Size(min = 5, max = 100, message = "Team service is greater than 5")
    private String service;

    @NotNull(message = "Team company field is required")
    private Long companyId;

    @NotNull(message = "Description is required")
    private String description;

}
