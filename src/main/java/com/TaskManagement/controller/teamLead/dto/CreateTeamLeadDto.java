package com.TaskManagement.controller.teamLead.dto;

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
public class CreateTeamLeadDto extends CreateUserDto {
    @NotNull(message = "Company id is required")
    private Long companyId;
}
