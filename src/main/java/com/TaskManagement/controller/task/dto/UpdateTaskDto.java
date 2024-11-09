package com.TaskManagement.controller.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UpdateTaskDto {
    private String taskName;
    private Long teamMemberId;
    private Long teamLeadId;
    private String description;
    private Date date;
    private int totalDate;
}
