package com.TaskManagement.controller.teamLead;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.team.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="teamLead")
public class TeamLead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User name is required")
    @Length(min = 3, max = 20,message = "Name must be greater name 3 and lower than 20")
    private String username;

    @NotNull(message = "Email is required")
    @Column(unique = true)
    @Email(message = "Require a valid email")
    private String email;

    @Column(unique = true)
    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @Length(min = 6, message = "Password must be greater than 6 character")
    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Team Field is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Company company;

    @NotNull(message = "Team id is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    private boolean active = true;
    private String role = "TeamLead";

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}