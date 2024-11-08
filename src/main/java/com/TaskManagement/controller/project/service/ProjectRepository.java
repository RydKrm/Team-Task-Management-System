package com.TaskManagement.controller.project.service;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    public List<Project> findByCompany(Company company);

    public Page<Project> findByStatus(boolean status, Pageable pageable);

    public Project findByName(String name);


}
