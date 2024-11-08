package com.TaskManagement.controller.teamMember.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.team.Team;
import com.TaskManagement.controller.teamMember.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>{
    public TeamMember findByEmailOrPhoneNumber(String email, String phoneNumber);

    public TeamMember findByEmail(String email);

    @Query("SELECT a FROM TeamMember a WHERE a.username LIKE CONCAT('%',:prefix, '%') OR a.phoneNumber LIKE CONCAT('%',:prefix, '%')")
    Page<TeamMember> findByNameOrPhoneNumberWithRegex(@Param("prefix") String prefix, Pageable pageable);

    @Query("SELECT a FROM TeamMember a WHERE a.active = :active AND (a.username LIKE CONCAT('%', :prefix, '%') OR a.phoneNumber LIKE CONCAT('%', :prefix, '%'))")
    Page<TeamMember> findByNameOrPhoneNumberOrActiveWithRegex(@Param("prefix") String prefix, Pageable pageable, @Param("active") boolean active);

    public Page<TeamMember> findByActive(boolean active, Pageable pageable);

    public Page<TeamMember> findByCompany(Company company, Pageable pageable);

    public Page<TeamMember> findByTeam(Team team, Pageable pageable);

}
