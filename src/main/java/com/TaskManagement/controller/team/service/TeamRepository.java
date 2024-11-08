package com.TaskManagement.controller.team.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TaskManagement.controller.team.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

    public Team findByName(String name);

    @Query("SELECT c FROM Team c WHERE c.name LIKE CONCAT('%',:prefix, '%')")
    Page<Team> findByNameWithRegex(@Param("prefix") String prefix, Pageable pageable);

    @Query("SELECT c FROM Team c WHERE c.active = :active AND c.name LIKE CONCAT('%', :prefix, '%')")
    Page<Team> findByNameOrActiveWithRegex(@Param("prefix") String prefix, Pageable pageable, @Param("active") boolean active);

    public Page<Team> findByActive(boolean active, Pageable pageable);

}
