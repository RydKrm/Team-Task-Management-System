package com.TaskManagement.controller.company;

import java.util.Map;

import com.TaskManagement.controller.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.controller.company.dto.CreateCompanyDto;
import com.TaskManagement.controller.company.dto.UpdateCompanyDto;
import com.TaskManagement.response.NegativeResponse;
import com.TaskManagement.response.PositiveResponse;
import com.TaskManagement.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/Company")
@Tag(name = "Company Controller", description = "Company API List")
public class CompanyController {
    @Autowired
    private final CompanyService companyService;

    @Operation(summary = "Create a new Company", description = "Add a new Company with given details")
    @PostMapping()
    public ResponseEntity<Response> createCompany(@RequestBody CreateCompanyDto data) {
        try {

            Company getCompany = companyService.createCompany(data);
            return ResponseEntity.ok(new PositiveResponse("Company Created", getCompany));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Company with pagination", description="Get all Company with pagination , search query return data list, total documents and page number")
    @GetMapping()
    public ResponseEntity<Response> getAllCompany(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(required = false) String search
    ) {
        try {
            return ResponseEntity
                    .ok(new PositiveResponse("Company list ", companyService.getAllCompany(page, limit, search)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse("Something wrong"));
        }
    }

    @Operation(summary = "Get All Active Company ", description = "Get the active Company list, which status is active")
    @GetMapping("/active")
    public ResponseEntity<Response> getActiveCompanyList(
        @RequestParam(defaultValue = "1") int page,
       @RequestParam(defaultValue = "10") int limit,
       @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> CompanyList = companyService.getAllActiveCompany(page, limit, search, true);
            return ResponseEntity.ok(new PositiveResponse("Active Company list", CompanyList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Not Active Company ", description = "Get the non active Company list, which status is active")
    @GetMapping("/not-active")
    public ResponseEntity<Response> getNotActiveCompanyList(
        @RequestParam(defaultValue = "1") int page,
       @RequestParam(defaultValue = "10") int limit,
       @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> CompanyList = companyService.getAllActiveCompany(page, limit, search, false);
            return ResponseEntity.ok(new PositiveResponse("Status false Company list", CompanyList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
    

    @Operation(summary = "Get Single Company", description = "Get Single Company by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSingleCompany(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new PositiveResponse("Company found by id", companyService.getCompanyById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary ="Update Company ", description="Company update require Company id and updated fields, password cannot be updated with this request")
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateCompany(@PathVariable Long id, @RequestBody UpdateCompanyDto data) {
        try {
            System.out.println("testing in controller");
            return ResponseEntity
                    .ok(new PositiveResponse("Company data updated successfully", companyService.updateCompany(id, data)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update Company status ", description = "This request will be toggle the Company status field")
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long id) {
        try {
            companyService.updateStatus(id);
            return ResponseEntity.ok(new PositiveResponse("Company status updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    
    @Operation(summary = "Company Delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.ok(new PositiveResponse("Company Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
}
