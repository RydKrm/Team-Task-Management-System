package com.TaskManagement.controller.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class CreateTaskDto {
    @NotNull(message = "Task name is required")
    private String taskName;

    private Long teamMemberId;

    private Long teamLeadId;

    private String description;

    @NotNull(message = "Task date is required")
    private Date date;

    private int totalDate;

}
