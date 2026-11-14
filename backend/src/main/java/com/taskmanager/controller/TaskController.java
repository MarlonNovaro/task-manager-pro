package com.taskmanager.controller;

import com.taskmanager.domain.enums.TaskStatus;
import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite que Angular/React se conecten sin problemas de CORS
public class TaskController {

    private final TaskService taskService;

    // 1. Obtener todas las tareas o filtrar por estado
    // GET: http://localhost:8080/api/tasks o
    // http://localhost:8080/api/tasks?status=PENDIENTE
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @RequestParam(required = false) TaskStatus status) {

        List<TaskResponseDTO> tasks = (status != null)
                ? taskService.getTasksByStatus(status)
                : taskService.getAllTasks();

        return ResponseEntity.ok(tasks); // Código 200 OK
    }

    // 2. Obtener una tarea por su ID
    // GET: http://localhost:8080/api/tasks/1
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // 3. Crear una nueva tarea
    // POST: http://localhost:8080/api/tasks
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(requestDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED); // Código 201 Created
    }

    // 4. Actualizar una tarea existente
    // PUT: http://localhost:8080/api/tasks/1
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO requestDTO) {

        TaskResponseDTO updatedTask = taskService.updateTask(id, requestDTO);
        return ResponseEntity.ok(updatedTask);
    }

    // 5. Eliminar una tarea por ID
    // DELETE: http://localhost:8080/api/tasks/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // Código 204 No Content
    }
}