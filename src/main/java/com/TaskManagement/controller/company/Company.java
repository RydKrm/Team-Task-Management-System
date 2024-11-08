package com.TaskManagement.controller.company;

import com.TaskManagement.controller.project.Project;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "company name is required")
    @Length(min = 3, max = 20, message = "Name must be greater name 3 and lower than 20")
    @Column(unique = true)
    private String name;

    @NotNull(message = "Description is required")
    private String description;
    
    private boolean active = true;

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }

}
