package com.taskmanager.service;

import com.taskmanager.domain.enums.TaskStatus;
import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO getTaskById(Long id);

    TaskResponseDTO createTask(TaskRequestDTO requestDTO);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO);

    void deleteTask(Long id);

    List<TaskResponseDTO> getTasksByStatus(TaskStatus status);
}