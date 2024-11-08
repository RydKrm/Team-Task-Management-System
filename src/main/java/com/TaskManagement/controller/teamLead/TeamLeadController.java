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
@RequestMapping("/api/v1/TeamMember")
@Tag(name = "TeamMember Controller", description = "TeamMember API List")
public class TeamLeadController {
    @Autowired
    private final TeamLeadService teamMemberService;

    @Operation(summary = "Create a new TeamMember", description = "Add a new TeamMember with given details")
    @PostMapping()
    public ResponseEntity<Response> createTeamMember(@Valid @RequestBody CreateTeamLeadDto data) {
        try {
            TeamLead getTeamMember = teamMemberService.createTeamMember(data);
            return ResponseEntity.ok(new PositiveResponse("TeamMember Created", getTeamMember));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All TeamMember with pagination", description="Get all TeamMember with pagination , search query return data list, total documents and page number")
    @GetMapping()
    public ResponseEntity<Response> getAllTeamMember(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(required = false) String search
    ) {
        try {
            return ResponseEntity
                    .ok(new PositiveResponse("TeamMember list ", teamMemberService.getAllTeamMember(page, limit, search)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse("Something wrong"));
        }
    }

    @Operation(summary = "TeamMember Login Controller", description = "TeamMember Login in request, Request with email and password")
    @PostMapping("/login")
    public ResponseEntity<Response> TeamMemberLogin(@Valid @RequestBody LoginDto data) {
        try {
            HashMap<String, Object> response = teamMemberService.TeamMemberLogin(data);
            return ResponseEntity.ok(new PositiveResponse("Login successfully ", response));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Active TeamMember ", description = "Get the active TeamMember list, which status is active")
    @GetMapping("/active")
    public ResponseEntity<Response> getActiveTeamMemberList(
        @RequestParam(defaultValue = "1") int page,
       @RequestParam(defaultValue = "10") int limit,
       @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> TeamMemberList = teamMemberService.getAllActiveTeamMember(page, limit, search, true);
            return ResponseEntity.ok(new PositiveResponse("Active TeamMember list", TeamMemberList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Not Active TeamMember ", description = "Get the non active TeamMember list, which status is active")
    @GetMapping("/turnOff")
    public ResponseEntity<Response> getNotActiveTeamMemberList(
        @RequestParam(defaultValue = "1") int page,
       @RequestParam(defaultValue = "10") int limit,
       @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> TeamMemberList = teamMemberService.getAllActiveTeamMember(page, limit, search, false);
            return ResponseEntity.ok(new PositiveResponse("Status false TeamMember list", TeamMemberList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
    

    @Operation(summary = "Get Single TeamMember", description = "Get Single TeamMember by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSingleTeamMember(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new PositiveResponse("TeamMember found by id", teamMemberService.getTeamMemberById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary ="Update TeamMember ", description="TeamMember update require TeamMember id and updated fields, password cannot be updated with this request")
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateTeamMember(@PathVariable Long id,@Valid @RequestBody UpdateTeamLeadDto data) {
        try {
            System.out.println("testing in controller");
            return ResponseEntity
                    .ok(new PositiveResponse("TeamMember data updated successfully", teamMemberService.updateTeamMember(id, data)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update TeamMember status ", description = "This request will be toggle the TeamMember status field")
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long id) {
        try {
            teamMemberService.updateStatus(id);
            return ResponseEntity.ok(new PositiveResponse("TeamMember status updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "TeamMember Update Password", description = "TeamMember old and new password is required to update")
    @PatchMapping("/update-password/{id}")
    public ResponseEntity<Response> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDto data) {
        try {
            teamMemberService.updatePassword(id, data);
            return ResponseEntity.ok(new PositiveResponse("Password update successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
    
    @Operation(summary = "TeamMember Delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteTeamMember(@PathVariable Long id) {
        try {
            teamMemberService.deleteTeamMember(id);
            return ResponseEntity.ok(new PositiveResponse("TeamMember Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

}
