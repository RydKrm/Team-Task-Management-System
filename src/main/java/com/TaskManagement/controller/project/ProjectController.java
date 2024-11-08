package com.TaskManagement.controller.project;


import com.TaskManagement.controller.project.dto.CreateProjectDto;
import com.TaskManagement.controller.project.dto.UpdateProjectDto;
import com.TaskManagement.controller.project.service.ProjectService;
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

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Project Controller", description = "Project Api list")
public class ProjectController {
    @Autowired
    private final ProjectService projectService;

    @Operation(summary = "Create a new Project")
    @PostMapping()
    public ResponseEntity<Response> createProject(@Valid @RequestBody CreateProjectDto data){
        try {
            return ResponseEntity.ok(new PositiveResponse("Project Created", projectService.createProject(data)));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get single project by id")
    @GetMapping("/{projectId}")
    public  ResponseEntity<Response> getSingleProject(@PathVariable Long projectId){
        try {
            return ResponseEntity.ok(new PositiveResponse("Project found successfully", projectService.getSingleProject(projectId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get all project")
    @GetMapping()
    public  ResponseEntity<Response> getProject(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            return ResponseEntity.ok(new PositiveResponse("Project found successfully", projectService.getAllProject(page,limit)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get all active project")
    @GetMapping("/active")
    public  ResponseEntity<Response> getAllActiveProject(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            return ResponseEntity.ok(new PositiveResponse("Project found successfully", projectService.getAllProjectByStatus(page,limit,true)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get all active project")
    @GetMapping("/not-active")
    public  ResponseEntity<Response> getAllNotActiveProject(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            return ResponseEntity.ok(new PositiveResponse("Project found successfully", projectService.getAllProjectByStatus(page,limit,false)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update project")
    @PatchMapping("/update/{projectId}")
    public ResponseEntity<Response> updateProject(@PathVariable Long projectId, @RequestBody UpdateProjectDto data){
        try {
            return ResponseEntity.ok(new PositiveResponse("Project updated successfully", projectService.updateProject(projectId, data)));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update project status by id")
    @PatchMapping("/update-status/{projectId}")
    public ResponseEntity<Response> updateProjectStatus(@PathVariable Long projectId, @RequestBody UpdateProjectDto data){
        try {
            projectService.updateStatus(projectId);
            return ResponseEntity.ok(new PositiveResponse("Project status updated successfully", null));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Delete project by id")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Response> deleteProject(@PathVariable Long projectId){
        try {
            projectService.deleteProject(projectId);
            return ResponseEntity.ok(new PositiveResponse("Project deleted successfully", null));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }
}
