package com.TaskManagement.controller.team;

import java.util.Map;

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

import com.TaskManagement.controller.team.Team;
import com.TaskManagement.controller.team.dto.CreateTeamDto;
import com.TaskManagement.controller.team.dto.UpdateTeamDto;
import com.TaskManagement.controller.team.service.TeamService;
import com.TaskManagement.response.NegativeResponse;
import com.TaskManagement.response.PositiveResponse;
import com.TaskManagement.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/team")
@Tag(name = "Team Controller", description = "Team API List")
public class TeamController {
    @Autowired
    private final TeamService teamService;

    @Operation(summary = "Create a new Team", description = "Add a new Team with given details")
    @PostMapping()
    public ResponseEntity<Response> createTeam(@Valid @RequestBody CreateTeamDto data) {
        try {
            Team getTeam = teamService.createTeam(data);
            return ResponseEntity.ok(new PositiveResponse("Team Created", getTeam));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Team with pagination", description="Get all Team with pagination , search query return data list, total documents and page number")
    @GetMapping()
    public ResponseEntity<Response> getAllTeamList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(required = false) String search
    ) {
        try {
            return ResponseEntity
                    .ok(new PositiveResponse("Team list ", teamService.getAllTeam(page, limit, search)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse("Something wrong"));
        }
    }

    @Operation(summary = "Get All Active Team ", description = "Get the active Team list, which status is active")
    @GetMapping("/active")
    public ResponseEntity<Response> getActiveTeamList(
        @RequestParam(defaultValue = "1") int page,
       @RequestParam(defaultValue = "10") int limit,
       @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> TeamList = teamService.getAllActiveTeam(page, limit, search, true);
            return ResponseEntity.ok(new PositiveResponse("Active Team list", TeamList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All Not Active Team ", description = "Get the non active Team list, which status is active")
    @GetMapping("/not-active")
    public ResponseEntity<Response> getNotActiveTeamList(
        @RequestParam(defaultValue = "1") int page,
       @RequestParam(defaultValue = "10") int limit,
       @RequestParam(required = false) String search
    ) {
        try {
            Map<String, Object> TeamList = teamService.getAllActiveTeam(page, limit, search, false);
            return ResponseEntity.ok(new PositiveResponse("Status false Team list", TeamList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get Single Team", description = "Get Single Team by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSingleTeam(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new PositiveResponse("Team found by id", teamService.getTeamById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary ="Update Team ", description="Team update require Team id and updated fields, password cannot be updated with this request")
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateTeam(@PathVariable Long id, @RequestBody UpdateTeamDto data) {
        try {
            System.out.println("testing in controller");
            return ResponseEntity
                    .ok(new PositiveResponse("Team data updated successfully", teamService.updateTeam(id, data)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update Team status ", description = "This request will be toggle the Team status field")
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long id) {
        try {
            teamService.updateStatus(id);
            return ResponseEntity.ok(new PositiveResponse("Team status updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Team Delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.ok(new PositiveResponse("Team Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
}
