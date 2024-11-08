package com.TaskManagement.controller.teamMember.dto;

import com.TaskManagement.dto.UpdateUserDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateTeamMemberDto extends UpdateUserDto {
    private Long companyId;
    private Long teamId;
}