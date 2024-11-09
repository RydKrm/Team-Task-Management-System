package com.TaskManagement.controller.task;


import com.TaskManagement.controller.task.dto.CreateTaskDto;
import com.TaskManagement.controller.task.dto.UpdateTaskDto;
import com.TaskManagement.controller.task.service.TaskService;
import com.TaskManagement.enums.TaskStatus;
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
@RequestMapping("/api/v1/task")
@Tag(name = "Task Crud Api List")
public class TaskController {
    @Autowired
    private final TaskService taskService;

    @Operation(summary = "Create Task")
    @PostMapping()
    public ResponseEntity<Response> CreateTask(@Valid @RequestBody CreateTaskDto data){
        try{
            return ResponseEntity.ok(new PositiveResponse("Task created successfully", taskService.createTask(data)));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get task by id")
    @GetMapping("/single/{taskId}")
    public ResponseEntity<Response> getSingleTask(@PathVariable Long taskId){
        try{
            return ResponseEntity.ok(new PositiveResponse("Task found successfully",taskService.getSingleTask(taskId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All task")
    @GetMapping("/all")
    public ResponseEntity<Response> getAllTask(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try{
            return ResponseEntity.ok(new PositiveResponse("Task found successfully",taskService.getAllTaskList(page,limit)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All task by name")
    @GetMapping("/by-name/{name}")
    public ResponseEntity<Response> getAllByName(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @PathVariable String name){
        try{
            return ResponseEntity.ok(new PositiveResponse("Task found successfully",taskService.getAllTaskByName(name, page, limit)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All by team member")
    @GetMapping("/by-team-member/{teamMemberId}")
    public ResponseEntity<Response> getAllByTeamMember(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @PathVariable Long teamMemberId){
        try{
            return ResponseEntity.ok(new PositiveResponse("Task found successfully",taskService.getAllTaskByTeamMember(teamMemberId,page,limit)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get All by team lead")
    @GetMapping("/by-team-lead/{teamLeadId}")
    public ResponseEntity<Response> getAllByTeamLead(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @PathVariable Long teamLeadId){
        try{
            return ResponseEntity.ok(new PositiveResponse("Task found successfully",taskService.getAllTaskByTeamLead(teamLeadId,page,limit)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update Task")
    @PatchMapping("/update/{taskId}")
    public ResponseEntity<Response> update(@PathVariable Long taskId, @RequestBody UpdateTaskDto data){
        try{
            return ResponseEntity.ok(new PositiveResponse("Task updated successfully",taskService.updateTask(taskId, data)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Update Task Status")
    @PatchMapping("/update-status/{taskId}/{status}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long taskId, @PathVariable TaskStatus status){
        try{
            taskService.updateStatus(taskId, status);
            return ResponseEntity.ok(new PositiveResponse("Task status updated successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }


    @Operation(summary = "Delete Task")
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Response> deleteTask(@PathVariable Long taskId){
        try{
            taskService.deleteTask(taskId);
            return ResponseEntity.ok(new PositiveResponse("Task deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new NegativeResponse(e.getMessage()));
        }
    }


}
