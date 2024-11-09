package com.TaskManagement.controller.teamLead.service;

import com.TaskManagement.config.JWTService;
import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.company.service.CompanyRepository;
import com.TaskManagement.controller.team.Team;
import com.TaskManagement.controller.team.service.TeamRepository;
import com.TaskManagement.controller.teamLead.TeamLead;
import com.TaskManagement.controller.teamLead.dto.CreateTeamLeadDto;
import com.TaskManagement.controller.teamLead.dto.UpdateTeamLeadDto;
import com.TaskManagement.dto.LoginDto;
import com.TaskManagement.dto.UpdatePasswordDto;
import com.TaskManagement.exceptions.AlreadyExistsException;
import com.TaskManagement.exceptions.ResourceNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamLeadService {
    @Autowired
    private final TeamRepository teamRepository;
    @Autowired
    private final CompanyRepository companyRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private final TeamLeadRepository TeamLeadRepository;
    @Autowired
    private final JWTService jwtService;
    
    public TeamLead createTeamLead(CreateTeamLeadDto data) {
        if (TeamLeadRepository.findByEmailOrPhoneNumber(data.getEmail(), data.getPhoneNumber()) != null) {
            throw new AlreadyExistsException(
                    "TeamLead already exists with email or PhoneNumber " + data.getEmail() + " " + data.getPhoneNumber());
        }

        Company company = companyRepository.findById(data.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found by id"));

        Team team = teamRepository.findById(data.getTeamId()).orElseThrow(()-> new ResourceNotFoundException("Team not found"));


        String hashedPassword = passwordEncoder.encode(data.getPassword());

        TeamLead newTeamLead = new TeamLead();
        newTeamLead.setUsername(data.getName());
        newTeamLead.setEmail(data.getEmail());
        newTeamLead.setPhoneNumber(data.getPhoneNumber());
        newTeamLead.setPassword(hashedPassword);
        newTeamLead.setCompany(company);
        newTeamLead.setTeam(team);
        TeamLeadRepository.save(newTeamLead);
        return newTeamLead;
    }

    public HashMap<String, Object> TeamLeadLogin(LoginDto data) {
        TeamLead getTeamLead = TeamLeadRepository.findByEmail(data.getEmail());
        if (getTeamLead == null) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        if (!passwordEncoder.matches(data.getPassword(), getTeamLead.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        getTeamLead.setPassword(null);

        String token = jwtService.generateToken(getTeamLead.getId(), "TeamLead", 10000000L);
        HashMap<String, Object> returnData = new HashMap<String, Object>();

        returnData.put("token", token);
        returnData.put("user", getTeamLead);

        return returnData;
    }

    public Map<String, Object> getAllTeamLead(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page-1,limit);

//        Page<TeamLead> TeamLeadPage = (search != null) ?
//                TeamLeadRepository.findByNameOrPhoneNumberWithRegex(search, pageable) :
//                TeamLeadRepository.findAll(pageable);

        Page<TeamLead> TeamLeadPage = TeamLeadRepository.getAllTeamLead(pageable);

        List<TeamLead> list = TeamLeadPage.getContent();
        
        long totalDocs = TeamLeadPage.getTotalElements();
        int totalPages = TeamLeadPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);

        return data;
    }
    
    public Map<String, Object> getAllActiveTeamLead(int page, int limit, String search, boolean active) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<TeamLead> TeamLeadPage;

        if (search != null) {
            TeamLeadPage = TeamLeadRepository.findByNameOrPhoneNumberOrActiveWithRegex(search, pageable, active);
        } else {
            TeamLeadPage = TeamLeadRepository.findByActive(active, pageable);
        }

        List<TeamLead> list = TeamLeadPage.getContent();
        long totalDocs = TeamLeadPage.getTotalElements();
        int totalPages = TeamLeadPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);
        return data;
    }
    
    public TeamLead getTeamLeadById(Long id) {
        return TeamLeadRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("TeamLead Not Found By id"));
    }

    public TeamLead updateTeamLead(Long id, UpdateTeamLeadDto data) {
        TeamLead getTeamLead = TeamLeadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeamLead not found by id"));
        if (data.getEmail() != null) {
            getTeamLead.setEmail(data.getEmail());
        }

        if (data.getName() != null) {
            getTeamLead.setUsername(data.getName());
        }

        if (data.getPhoneNumber() != null) {
            getTeamLead.setPhoneNumber(data.getPhoneNumber());
        }

        if (data.getCompanyId() != null) {
            Company company = companyRepository.findById(data.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found by id"));
            getTeamLead.setCompany(company);
        }

        if (data.getTeamId() != null) {
            Team team = teamRepository.findById(data.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found by id"));
            getTeamLead.setTeam(team);
        }

        TeamLeadRepository.save(getTeamLead);
        return getTeamLead;
    }

    public void updatePassword(Long id, UpdatePasswordDto data) {
        TeamLead getTeamLead = TeamLeadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeamLead not found by id"));
        boolean isPasswordMatch = passwordEncoder.matches(data.getOldPassword(), getTeamLead.getPassword());

        if (!isPasswordMatch) {
            throw new ValidationException("Old password did not match");
        }

        String hashPassword = passwordEncoder.encode(data.getNewPassword());

        getTeamLead.setPassword(hashPassword);

        TeamLeadRepository.save(getTeamLead);
    }

    public void updateStatus(Long id) {
        TeamLead getTeamLead = TeamLeadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeamLead not found by id"));
        if (getTeamLead.getActive() == true) {
            getTeamLead.setActive(false);
        } else {
            getTeamLead.setActive(true);
        }

        TeamLeadRepository.save(getTeamLead);
    }

    public void deleteTeamLead(Long id) {
        TeamLead TeamLead = TeamLeadRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        TeamLeadRepository.delete(TeamLead);
    }

}
