package com.TaskManagement.controller.task.service;

import com.TaskManagement.controller.task.Task;
import com.TaskManagement.controller.task.dto.CreateTaskDto;
import com.TaskManagement.controller.task.dto.GetTaskDto;
import com.TaskManagement.controller.task.dto.UpdateTaskDto;
import com.TaskManagement.controller.teamLead.TeamLead;
import com.TaskManagement.controller.teamLead.service.TeamLeadRepository;
import com.TaskManagement.controller.teamMember.TeamMember;
import com.TaskManagement.controller.teamMember.service.TeamMemberRepository;
import com.TaskManagement.enums.TaskStatus;
import com.TaskManagement.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
  @Autowired
  TaskRepository taskRepository;

  @Autowired
  TeamLeadRepository teamLeadRepository;

  @Autowired
  TeamMemberRepository teamMemberRepository;

  public Task createTask(CreateTaskDto data){
      Task newTask = new Task();

      if(data.getTeamMemberId() != null){
          TeamMember teamMember = teamMemberRepository.findById(data.getTeamMemberId()).orElseThrow(() -> new ResourceNotFoundException("Team member not found"));
          newTask.setTeamMember(teamMember);
      }

      if(data.getTeamLeadId() != null){
          TeamLead teamLead = teamLeadRepository.findById(data.getTeamLeadId()).orElseThrow(()-> new ResourceNotFoundException("Team Lead not found"));
          newTask.setTeamLead(teamLead);
      }

      newTask.setTaskName(data.getTaskName());
      newTask.setDate(data.getDate());
      newTask.setDescription(data.getDescription());

      taskRepository.save(newTask);

      return newTask;
  }

  public Task getSingleTask(Long taskId){
      return taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found by id"));
  }

  public Map<String, Object> getAllTaskList(int page, int limit){
      Pageable pageable = PageRequest.of(page-1, limit);
      Page<GetTaskDto> taskList = taskRepository.getAllTask(pageable);

      Map<String, Object> data = new HashMap<>();
      data.put("list", taskList.getContent());
      data.put("totalDoc", taskList.getTotalElements());
      data.put("totalPage", taskList.getTotalPages());

      return data;
  }

  public Map<String, Object> getAllTaskByName(String name,int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<GetTaskDto> taskList = taskRepository.findByTaskName(name,pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("list", taskList.getContent());
        data.put("totalDoc", taskList.getTotalElements());
        data.put("totalPage", taskList.getTotalPages());

        return data;
    }


    public Map<String, Object> getAllTaskByTeamLead(Long teamLeadId,int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<GetTaskDto> taskList = taskRepository.findByTeamLead(teamLeadId,pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("list", taskList.getContent());
        data.put("totalDoc", taskList.getTotalElements());
        data.put("totalPage", taskList.getTotalPages());

        return data;
    }

    public Map<String, Object> getAllTaskByTeamMember(Long teamMemberId,int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<GetTaskDto> taskList = taskRepository.findByTeamMember(teamMemberId,pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("list", taskList.getContent());
        data.put("totalDoc", taskList.getTotalElements());
        data.put("totalPage", taskList.getTotalPages());

        return data;
    }

  public Task updateTask(Long taskId, UpdateTaskDto data){
      Task getTask = taskRepository.findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Task not found"));

      if(data.getTaskName()!=null){
          getTask.setTaskName(data.getTaskName());
      }

      if(data.getDescription() != null){
          getTask.setDescription(data.getDescription());
      }

      if(data.getTeamMemberId() != null){
          TeamMember teamMember = teamMemberRepository.findById(data.getTeamLeadId()).orElseThrow(()-> new ResourceNotFoundException("Team member not found"));
          getTask.setTeamMember(teamMember);
      }

      if(data.getTeamLeadId() != null){
          TeamLead teamLead = teamLeadRepository.findById(data.getTeamLeadId()).orElseThrow(()-> new ResourceNotFoundException("Team lead not found"));
      }

      if(data.getDate() != null){
          getTask.setDate(data.getDate());
      }

      if(data.getTotalDate() != 0){
          getTask.setTotalDays(data.getTotalDate());
      }
      return getTask;

  }

  public void updateStatus(Long taskId, TaskStatus taskStatus){
      Task task = taskRepository.findById(taskId).orElseThrow(()-> new ResourceNotFoundException(" Task not found"));
      task.setStatus(taskStatus);
      taskRepository.save(task);
  }

  public void deleteTask(Long taskId){
      Task task = taskRepository.findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Task not found by Id"));

      taskRepository.deleteById(taskId);
  }

}
