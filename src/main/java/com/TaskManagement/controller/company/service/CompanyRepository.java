package com.TaskManagement.controller.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TaskManagement.controller.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

    public Company findByName(String name);

    @Query("SELECT c FROM Company c WHERE c.name LIKE CONCAT('%',:prefix, '%')")
    Page<Company> findByNameWithRegex(@Param("prefix") String prefix, Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.active = :active AND (c.name LIKE CONCAT('%', :prefix, '%'))")
    Page<Company> findByNameOrActiveWithRegex(@Param("prefix") String prefix, Pageable pageable, @Param("active") boolean active);

    public Page<Company> findByActive(boolean active, Pageable pageable);

}
