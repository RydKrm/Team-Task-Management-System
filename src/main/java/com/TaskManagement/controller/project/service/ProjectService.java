package com.TaskManagement.controller.project.service;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.company.service.CompanyRepository;
import com.TaskManagement.controller.project.Project;
import com.TaskManagement.controller.project.dto.CreateProjectDto;
import com.TaskManagement.controller.project.dto.UpdateProjectDto;
import com.TaskManagement.exceptions.AlreadyExistsException;
import com.TaskManagement.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ProjectRepository projectRepository;

    public Project createProject(CreateProjectDto data){
        Company getCompany = companyRepository.findById(data.getCompanyId()).orElseThrow(
                ()-> new ResourceNotFoundException("Company not found by id")
        );

        Project isExists = projectRepository.findByName(data.getName());
        if( isExists != null){
            throw new AlreadyExistsException("Project with same name already exists");
        }

        Project newProject = new Project();

        newProject.setCompany(getCompany);
        newProject.setName(data.getName());
        newProject.setDescription(data.getDescription());

        projectRepository.save(newProject);
        return newProject;
    }

    public Project getSingleProject(Long projectId){
        return projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException("Project not found by Id"));
    }

    public Company getProjectCompany(Long projectId) {
        return projectRepository.findById(projectId)
                .map(Project::getCompany)
                .orElse(null);
    }

    public Map<String, Object> getAllProject(int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Project> projectList = projectRepository.findAll(pageable);

        Map<String, Object> list =  new HashMap<>();
        list.put("totalDoc",projectList.getTotalElements());
        list.put("totalPage", projectList.getTotalPages());
        list.put("data", projectList.getContent());

        return list;
    }

    public Map<String, Object> getAllProjectByStatus(int page, int limit, boolean status){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Project> projectList = projectRepository.findByStatus(status, pageable);
        Map<String, Object> list =  new HashMap<>();
        list.put("totalDoc",projectList.getTotalElements());
        list.put("totalPage", projectList.getTotalPages());
        list.put("data", projectList.getContent());
        return list;
    }

    public Project updateProject(Long projectId, UpdateProjectDto data){
        Project getProject = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException("Project not found by id"));
        if(data.getName() != null){
            getProject.setName(data.getName());
        }

        if(data.getDescription() != null){
            getProject.setDescription(data.getDescription());
        }

        if(data.getCompanyId() != null){
            Company company = companyRepository.findById(data.getCompanyId()).orElseThrow(()-> new ResourceNotFoundException("Company not found by id"));
            getProject.setCompany(company);
        }

        projectRepository.save(getProject);
        return getProject;

    }

    public void updateStatus(Long projectId){
        Project getProject = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException("Project not found by id"));
        getProject.setStatus(!getProject.getStatus());
        projectRepository.save(getProject);
    }

    public void deleteProject(Long projectId){
        projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException("Project not found by Id"));
        projectRepository.deleteById(projectId);
    }



}