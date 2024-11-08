package com.TaskManagement.controller.teamLead;

import com.TaskManagement.controller.teamLead.dto.CreateTeamLeadDto;
import com.TaskManagement.controller.teamLead.dto.UpdateTeamLeadDto;
import com.TaskManagement.controller.teamLead.service.TeamLeadService;
import com.TaskManagement.dto.LoginDto;
import com.TaskManagement.dto.UpdatePasswordDto;
import com.TaskManagement.response.NegativeResponse;
import com.TaskManagement.response.PositiveResponse;
import com.TaskManagement.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/team-lead")
@Tag(name = "TeamLead Controller", description = "TeamLead API List")
public class TeamLeadController {
    @Autowired
    private final TeamLeadService TeamLeadService;

    @Operation(summary = "Create a new TeamLead", description = "Add a new TeamLead with given details")
    @PostMapping()
    public ResponseEntity<Response> createTeamLead(@Valid @RequestBody CreateTeamLeadDto data) {
        try {
            TeamLead getTeamLead = TeamLeadService.createTeamLead(data);
            return ResponseEntity.ok(new PositiveResponse("TeamLead Created", getTeamLead));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All TeamLead with pagination", description="Get all TeamLead with pagination , search query return data list, total documents and page number")
    @GetMapping()
    public ResponseEntity<Response> getAllTeamLead(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(required = false) String search
    ) {
        try {
            return ResponseEntity
                    .ok(new PositiveResponse("TeamLead list ", TeamLeadService.getAllTeamLead(page, limit, search)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse("Something wrong"));
        }
    }

    @Operation(summary = "TeamLead Login Controller", description = "TeamLead Login in request, Request with email and password")
    @PostMapping("/login")
    public ResponseEntity<Response> TeamLeadLogin(@Valid @RequestBody LoginDto data) {
        try {
            HashMap<String, Object> response = TeamLeadService.TeamLeadLogin(data);
            return ResponseEntity.ok(new PositiveResponse("Login successfully ", response));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Active TeamLead ", description = "Get the active TeamLead list, which status is active")
    @GetMapping("/active")
    public ResponseEntity<Response> getActiveTeamLeadList(
        @RequestParam(defaultValue = "1") int page,
       @RequestParam(defaultValue = "10") int limit,
       @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> TeamLeadList = TeamLeadService.getAllActiveTeamLead(page, limit, search, true);
            return ResponseEntity.ok(new PositiveResponse("Active TeamLead list", TeamLeadList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Not Active TeamLead ", description = "Get the non active TeamLead list, which status is active")
    @GetMapping("/turnOff")
    public ResponseEntity<Response> getNotActiveTeamLeadList(
        @RequestParam(defaultValue = "1") int page,
       @RequestParam(defaultValue = "10") int limit,
       @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> TeamLeadList = TeamLeadService.getAllActiveTeamLead(page, limit, search, false);
            return ResponseEntity.ok(new PositiveResponse("Status false TeamLead list", TeamLeadList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
    

    @Operation(summary = "Get Single TeamLead", description = "Get Single TeamLead by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSingleTeamLead(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new PositiveResponse("TeamLead found by id", TeamLeadService.getTeamLeadById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary ="Update TeamLead ", description="TeamLead update require TeamLead id and updated fields, password cannot be updated with this request")
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateTeamLead(@PathVariable Long id,@Valid @RequestBody UpdateTeamLeadDto data) {
        try {
            System.out.println("testing in controller");
            return ResponseEntity
                    .ok(new PositiveResponse("TeamLead data updated successfully", TeamLeadService.updateTeamLead(id, data)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update TeamLead status ", description = "This request will be toggle the TeamLead status field")
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long id) {
        try {
            TeamLeadService.updateStatus(id);
            return ResponseEntity.ok(new PositiveResponse("TeamLead status updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "TeamLead Update Password", description = "TeamLead old and new password is required to update")
    @PatchMapping("/update-password/{id}")
    public ResponseEntity<Response> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDto data) {
        try {
            TeamLeadService.updatePassword(id, data);
            return ResponseEntity.ok(new PositiveResponse("Password update successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
    
    @Operation(summary = "TeamLead Delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteTeamLead(@PathVariable Long id) {
        try {
            TeamLeadService.deleteTeamLead(id);
            return ResponseEntity.ok(new PositiveResponse("TeamLead Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

}
