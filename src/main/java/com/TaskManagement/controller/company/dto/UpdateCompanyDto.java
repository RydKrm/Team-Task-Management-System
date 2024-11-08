package com.TaskManagement.controller.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateCompanyDto {
    private String name;
    private String description;
}