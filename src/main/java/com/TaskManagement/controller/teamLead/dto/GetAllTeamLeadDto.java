package com.TaskManagement.controller.teamLead.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class GetAllTeamLeadDto {
    private String username;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String teamName;
}


