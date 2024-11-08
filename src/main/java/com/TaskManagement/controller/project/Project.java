package com.TaskManagement.controller.project;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Project name is required")
    @Column(unique = true,name = "name")
    private String name;

    @NotNull(message = "Project description is required")
    @Column(name = "description")
    private String description;

    private boolean status = true;

    @NotNull(message = "Project company is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    /*
    * Many-to-Many Relation
    * Project-to-Team
    * In a project multiple Team can be involves
    * And a team can join multiple project in a same time
    * */

    @ManyToMany
    @JoinTable(name = "project_team", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name="team_id"))
    private Set<Team> teamList = new HashSet<>();

    public boolean getStatus(){
        return this.status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

}
