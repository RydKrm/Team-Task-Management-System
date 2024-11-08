package com.TaskManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateUserDto {
    private String name;
    private String email;
    private String phoneNumber;
}