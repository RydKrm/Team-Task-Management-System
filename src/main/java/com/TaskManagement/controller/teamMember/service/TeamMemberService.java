package com.TaskManagement.controller.teamMember.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.company.service.CompanyRepository;
import com.TaskManagement.controller.teamMember.*;
import com.TaskManagement.controller.teamMember.dto.CreateTeamMemberDto;
import com.TaskManagement.controller.teamMember.dto.UpdateTeamMemberDto;
import com.TaskManagement.controller.team.Team;
import com.TaskManagement.controller.team.service.TeamRepository;
import com.TaskManagement.dto.LoginDto;
import com.TaskManagement.dto.UpdatePasswordDto;
import com.TaskManagement.exceptions.AlreadyExistsException;
import com.TaskManagement.exceptions.ResourceNotFoundException;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMemberService {
    @Autowired
    private final TeamRepository teamRepository;
    private final CompanyRepository companyRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TeamMemberRepository teamMemberRepository;

    public TeamMember createTeamMember(CreateTeamMemberDto data) {
        if (teamMemberRepository.findByEmailOrPhoneNumber(data.getEmail(), data.getPhoneNumber()) != null) {
            throw new AlreadyExistsException(
                    "TeamMember already exists with email or PhoneNumber " + data.getEmail() + " " + data.getPhoneNumber());
        }
        
        Company company = companyRepository.findById(data.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found by id"));
        
        Team team = teamRepository.findById(data.getTeamId()).orElseThrow(() -> new ResourceNotFoundException("Team is not found by Id"));

        String hashedPassword = passwordEncoder.encode(data.getPassword());

        TeamMember newTeamMember = new TeamMember();
        newTeamMember.setUsername(data.getName());
        newTeamMember.setEmail(data.getEmail());
        newTeamMember.setPhoneNumber(data.getPhoneNumber());
        newTeamMember.setPassword(hashedPassword);
        newTeamMember.setCompany(company);
        newTeamMember.setTeam(team);

        teamMemberRepository.save(newTeamMember);

        return newTeamMember;
    }

    public HashMap<String, Object> TeamMemberLogin(LoginDto data) {
        TeamMember getTeamMember = teamMemberRepository.findByEmail(data.getEmail());
        if (getTeamMember == null) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        if (!passwordEncoder.matches(data.getPassword(), getTeamMember.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        getTeamMember.setPassword(null);

        String token = "jwtToken";
        HashMap<String, Object> returnData = new HashMap<String, Object>();

        returnData.put("token", token);
        returnData.put("user", getTeamMember);

        return returnData;
    }

    public Map<String, Object> getAllTeamMember(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page-1,limit);

        Page<TeamMember> TeamMemberPage = (search != null) ?
                teamMemberRepository.findByNameOrPhoneNumberWithRegex(search, pageable) :
                teamMemberRepository.findAll(pageable);

        List<TeamMember> list = TeamMemberPage.getContent();
        System.out.println("TeamMember list " + list);
        
        long totalDocs = TeamMemberPage.getTotalElements();
        int totalPages = TeamMemberPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);

        return data;
    }
    
    public Map<String, Object> getAllActiveTeamMember(int page, int limit, String search, boolean active) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<TeamMember> TeamMemberPage;

        if (search != null) {
            TeamMemberPage = teamMemberRepository.findByNameOrPhoneNumberOrActiveWithRegex(search, pageable, active);
        } else {
            TeamMemberPage = teamMemberRepository.findByActive(active, pageable);
        }

        List<TeamMember> list = TeamMemberPage.getContent();
        long totalDocs = TeamMemberPage.getTotalElements();
        int totalPages = TeamMemberPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);
        return data;
    }
    
    public TeamMember getTeamMemberById(Long id) {
        return teamMemberRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("TeamMember Not Found By id"));
    }

    public TeamMember updateTeamMember(Long id, UpdateTeamMemberDto data) {
        TeamMember getTeamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeamMember not found by id"));
        if (data.getEmail() != null) {
            getTeamMember.setEmail(data.getEmail());
        }

        if (data.getName() != null) {
            getTeamMember.setUsername(data.getName());
        }

        if (data.getPhoneNumber() != null) {
            getTeamMember.setPhoneNumber(data.getPhoneNumber());
        }

        if (data.getCompanyId() != null) {
            Company company = companyRepository.findById(data.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found by id"));
            getTeamMember.setCompany(company);
        }
        
        if (data.getTeamId() != null) {
                    Team team = teamRepository.findById(data.getCompanyId()).orElseThrow(() -> new ResourceNotFoundException("Company not found by id"));
            getTeamMember.setTeam(team);
        }

        teamMemberRepository.save(getTeamMember);
        return getTeamMember;
    }

    public void updatePassword(Long id, UpdatePasswordDto data) {
        TeamMember getTeamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeamMember not found by id"));
        boolean isPasswordMatch = passwordEncoder.matches(data.getOldPassword(), getTeamMember.getPassword());

        if (!isPasswordMatch) {
            throw new ValidationException("Old password did not match");
        }

        String hashPassword = passwordEncoder.encode(data.getNewPassword());

        getTeamMember.setPassword(hashPassword);

        teamMemberRepository.save(getTeamMember);
    }

    public void updateStatus(Long id) {
        TeamMember getTeamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeamMember not found by id"));
        if (getTeamMember.getActive() == true) {
            getTeamMember.setActive(false);
        } else {
            getTeamMember.setActive(true);
        }

        teamMemberRepository.save(getTeamMember);
    }

    public void deleteTeamMember(Long id) {
        TeamMember teamMember = teamMemberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        teamMemberRepository.delete(teamMember);
    }

}
