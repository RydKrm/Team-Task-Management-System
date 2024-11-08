package com.TaskManagement.controller.team.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateTeamDto {
    private String name;
    private String service;
    private Long companyId;
    private String description;
}