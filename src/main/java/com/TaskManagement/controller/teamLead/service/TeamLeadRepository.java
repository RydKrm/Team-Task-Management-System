package com.TaskManagement.controller.teamLead.service;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.team.Team;
import com.TaskManagement.controller.teamLead.TeamLead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamLeadRepository extends JpaRepository<TeamLead, Long>{
    public TeamLead findByEmailOrPhoneNumber(String email, String phoneNumber);

    public TeamLead findByEmail(String email);

    @Query("SELECT a FROM TeamMember a WHERE a.username LIKE CONCAT('%',:prefix, '%') OR a.phoneNumber LIKE CONCAT('%',:prefix, '%')")
    Page<TeamLead> findByNameOrPhoneNumberWithRegex(@Param("prefix") String prefix, Pageable pageable);

    @Query("SELECT a FROM TeamMember a WHERE a.active = :active AND (a.username LIKE CONCAT('%', :prefix, '%') OR a.phoneNumber LIKE CONCAT('%', :prefix, '%'))")
    Page<TeamLead> findByNameOrPhoneNumberOrActiveWithRegex(@Param("prefix") String prefix, Pageable pageable, @Param("active") boolean active);

    public Page<TeamLead> findByActive(boolean active, Pageable pageable);

    public Page<TeamLead> findByCompany(Company company, Pageable pageable);

    public Page<TeamLead> findByTeam(Team team, Pageable pageable);

}
