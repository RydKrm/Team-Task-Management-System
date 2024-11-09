package com.TaskManagement.controller.task.service;


import com.TaskManagement.controller.task.Task;
import com.TaskManagement.controller.task.dto.GetTaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

public interface TaskRepository extends JpaRepository<Task, Long>{

    @Query("SELECT new com.TaskManagement.controller.task.dto.GetTaskDto(t.taskName, t.description, tm.id, tm.username, tl.id, tl.username, t.date, t.totalDays) " +
            "FROM Task t LEFT JOIN t.teamLead tl LEFT JOIN t.teamMember tm")
    Page<GetTaskDto> getAllTask(Pageable pageable);


//    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.teamLead LEFT JOIN FETCH t.teamMember")
//    Page<Task> getAllTasks(Pageable pageable);


    @Query("SELECT new com.TaskManagement.controller.task.dto.GetTaskDto(t.taskName, t.description, tm.id, tm.username, tl.id, tl.username, t.date, t.totalDays) " +
            "FROM Task t LEFT JOIN t.teamLead tl LEFT JOIN t.teamMember tm " +
            "WHERE t.taskName = :taskName")
    Page<GetTaskDto> findByTaskName(@Param("taskName") String taskName, Pageable pageable);

    @Query("SELECT new com.TaskManagement.controller.task.dto.GetTaskDto(t.taskName, t.description, tm.id, tm.username, tl.id, tl.username, t.date, t.totalDays) " +
            "FROM Task t LEFT JOIN t.teamLead tl LEFT JOIN t.teamMember tm " +
            "WHERE t.teamLead.id = :teamLeadId")
    Page<GetTaskDto> findByTeamLead(@Param("teamLeadId") Long teamLeadId, Pageable pageable);

    @Query("SELECT new com.TaskManagement.controller.task.dto.GetTaskDto(t.taskName, t.description, tm.id, tm.username, tl.id, tl.username, t.date, t.totalDays) " +
            "FROM Task t LEFT JOIN t.teamLead tl LEFT JOIN t.teamMember tm " +
            "WHERE t.teamMember.id = :teamMemberId")
    Page<GetTaskDto> findByTeamMember(@Param("teamMemberId") Long teamMemberId, Pageable pageable);

}
