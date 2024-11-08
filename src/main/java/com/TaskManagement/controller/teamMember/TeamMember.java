package com.TaskManagement.controller.teamMember;

import org.hibernate.validator.constraints.Length;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.team.Team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name="teamMember")
public class TeamMember {
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
    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    @NotNull(message = "Team Field is required")
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

    private boolean active = true;
    private String role = "TeamMember";

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
