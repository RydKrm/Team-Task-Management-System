package com.TaskManagement.controller.task;


import com.TaskManagement.controller.teamLead.TeamLead;
import com.TaskManagement.controller.teamMember.TeamMember;
import com.TaskManagement.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Task name is required")
    private String taskName;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    private TeamLead teamLead;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeamMember teamMember;

    @NotNull(message = "Date is required")
    private Date date;

    private int totalDays;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

}
