package com.TaskManagement.controller.teamMember.dto;

import com.TaskManagement.dto.CreateUserDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateTeamMemberDto extends CreateUserDto {
    @NotNull(message = "Company id is required")
    private Long companyId;
    @NotNull(message = "Team id is required")
    private Long teamId;
}
