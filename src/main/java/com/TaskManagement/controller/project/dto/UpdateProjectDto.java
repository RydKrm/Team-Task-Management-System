package com.TaskManagement.controller.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateProjectDto {
  private String name;
  private String description;
  private Long companyId;
}
