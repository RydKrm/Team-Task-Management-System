package com.TaskManagement.controller.team.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.company.service.CompanyRepository;
import com.TaskManagement.controller.team.Team;
import com.TaskManagement.controller.team.dto.CreateTeamDto;
import com.TaskManagement.controller.team.dto.UpdateTeamDto;
import com.TaskManagement.exceptions.AlreadyExistsException;
import com.TaskManagement.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    @Autowired
    private final TeamRepository teamRepository;
    private final CompanyRepository companyRepository;


    public Team createTeam(CreateTeamDto data) {
        if (teamRepository.findByName(data.getName()) != null) {
            throw new AlreadyExistsException(
                    "Team already exists with name " + data.getName());
        }

        Company company = companyRepository.findById(data.getCompanyId()).orElseThrow(()-> new ResourceNotFoundException("Company not found with id"));

        Team newTeam = new Team();
        newTeam.setName(data.getName());
        newTeam.setService(data.getService());
        newTeam.setCompany(company);
        newTeam.setDescription(data.getDescription());
        teamRepository.save(newTeam);
        return newTeam;
    }

    public Map<String, Object> getAllTeam(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page-1,limit);

        Page<Team> TeamPage = (search != null) ?
                teamRepository.findByNameWithRegex(search, pageable) :
                teamRepository.findAll(pageable);

        List<Team> list = TeamPage.getContent();
        long totalDocs = TeamPage.getTotalElements();
        int totalPages = TeamPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);

        return data;
    }
    
    public Map<String, Object> getAllActiveTeam(int page, int limit, String search, boolean active) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Team> TeamPage;

        if (search != null) {
            TeamPage = teamRepository.findByNameOrActiveWithRegex(search, pageable, active);
        } else {
            TeamPage = teamRepository.findByActive(active, pageable);
        }

        List<Team> list = TeamPage.getContent();
        long totalDocs = TeamPage.getTotalElements();
        int totalPages = TeamPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);
        return data;
    }
    
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Team Not Found By id"));
    }

    public Team updateTeam(Long id, UpdateTeamDto data) {
        Team getTeam = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found by id"));
        System.out.println(data.getName());
        if (data.getDescription() != null) {
            getTeam.setDescription(data.getDescription());
        }

        if (data.getName() != null) {
            getTeam.setName(data.getName());
        }
        teamRepository.save(getTeam);
        return getTeam;
    }


    public void updateStatus(Long id) {
        Team getTeam = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found by id"));
        // if (getTeam.getActive() == true) {
        //     getTeam.setActive(false);
        // } else {
        //     getTeam.setActive(true);
        // }

        getTeam.setActive(!getTeam.getActive());
        teamRepository.save(getTeam);
    }

    public void deleteTeam(Long id) {
        Team Team = teamRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Team not found by id "));
        teamRepository.delete(Team);
    }

}

