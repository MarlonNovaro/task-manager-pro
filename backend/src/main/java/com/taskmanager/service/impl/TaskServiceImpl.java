package com.taskmanager.service.impl;

import com.taskmanager.domain.entity.Task;
import com.taskmanager.domain.enums.TaskStatus;
import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = findTaskOrThrow(id);
        return mapToResponseDTO(task);
    }

    @Override
    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Task task = Task.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .status(requestDTO.getStatus() != null ? requestDTO.getStatus() : TaskStatus.PENDIENTE)
                .priority(requestDTO.getPriority())
                .dueDate(requestDTO.getDueDate())
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToResponseDTO(savedTask);
    }

    @Override
    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {
        Task task = findTaskOrThrow(id);

        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        if (requestDTO.getStatus() != null) {
            task.setStatus(requestDTO.getStatus());
        }
        task.setPriority(requestDTO.getPriority());
        task.setDueDate(requestDTO.getDueDate());

        Task updatedTask = taskRepository.save(task);
        return mapToResponseDTO(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = findTaskOrThrow(id);
        taskRepository.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // ========================================================
    // Métodos auxiliares privados (Buenas prácticas / Clean Code)
    // ========================================================

    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con el ID: " + id));
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}