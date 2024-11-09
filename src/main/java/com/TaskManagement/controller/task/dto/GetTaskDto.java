package com.TaskManagement.controller.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GetTaskDto {
    private String taskName;
    private String description;
    private Long teamMemberId;
    private String teamMemberName;
    private Long teamLeadId;
    private String teamLeadName;
    private Date date;
    private int totalDays;
}
