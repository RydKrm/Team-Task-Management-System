package com.TaskManagement.controller.team;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.teamMember.TeamMember;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "name is required")
    @Length(min = 3, max = 50,message = "Name must be greater name 3 and lower than 20")
    private String name;
   
    @NotNull(message = "Service is required")
    @Length(min = 3, max = 50,message = "Name must be greater name 3 and lower than 20")
    private String service;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Company is required")
    @ManyToOne(fetch = FetchType.EAGER)
    // @JsonIgnore
    private Company company;
    
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<TeamMember> teamMember;

    private boolean active = true;

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active){
        this.active = active;
    }


}
