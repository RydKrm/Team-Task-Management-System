package com.TaskManagement.controller.company.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.TaskManagement.controller.company.Company;
import com.TaskManagement.controller.company.dto.CreateCompanyDto;
import com.TaskManagement.controller.company.dto.UpdateCompanyDto;
import com.TaskManagement.exceptions.AlreadyExistsException;
import com.TaskManagement.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
    @Autowired
    private final CompanyRepository CompanyRepository;


    public Company createCompany(CreateCompanyDto data) {
        if (CompanyRepository.findByName(data.getName()) != null) {
            throw new AlreadyExistsException(
                    "Company already exists with name " + data.getName());
        }
        Company newCompany = new Company();
        newCompany.setName(data.getName());
        newCompany.setDescription(data.getDescription());
        CompanyRepository.save(newCompany);
        return newCompany;
    }

    public Map<String, Object> getAllCompany(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page-1,limit);

        Page<Company> CompanyPage = (search != null) ?
                CompanyRepository.findByNameWithRegex(search, pageable) :
                CompanyRepository.findAll(pageable);

        List<Company> list = CompanyPage.getContent();
        long totalDocs = CompanyPage.getTotalElements();
        int totalPages = CompanyPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);

        return data;
    }
    
    public Map<String, Object> getAllActiveCompany(int page, int limit, String search, boolean active) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Company> CompanyPage;

        if (search != null) {
            CompanyPage = CompanyRepository.findByNameOrActiveWithRegex(search, pageable, active);
        } else {
            CompanyPage = CompanyRepository.findByActive(active, pageable);
        }

        List<Company> list = CompanyPage.getContent();
        long totalDocs = CompanyPage.getTotalElements();
        int totalPages = CompanyPage.getTotalPages();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("totalDocs", totalDocs);
        data.put("totalPages", totalPages);
        return data;
    }
    
    public Company getCompanyById(Long id) {
        return CompanyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Company Not Found By id"));
    }

    public Company updateCompany(Long id, UpdateCompanyDto data) {
        Company getCompany = CompanyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found by id"));
        System.out.println(data.getName());
        if (data.getDescription() != null) {
            getCompany.setDescription(data.getDescription());
        }

        if (data.getName() != null) {
            getCompany.setName(data.getName());
        }
        CompanyRepository.save(getCompany);
        return getCompany;
    }


    public void updateStatus(Long id) {
        Company getCompany = CompanyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found by id"));

        getCompany.setActive(!getCompany.getActive());
        CompanyRepository.save(getCompany);
    }

    public void deleteCompany(Long id) {
        Company Company = CompanyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Company not found by id "));
        CompanyRepository.delete(Company);
    }

}
