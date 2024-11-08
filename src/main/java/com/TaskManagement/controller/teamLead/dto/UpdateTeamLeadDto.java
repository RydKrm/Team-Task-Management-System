package com.TaskManagement.controller.teamLead.dto;

import com.TaskManagement.dto.UpdateUserDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateTeamLeadDto extends UpdateUserDto {
    private Long companyId;
}