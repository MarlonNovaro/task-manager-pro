package com.taskmanager.service;

import com.taskmanager.domain.entity.Task;
import com.taskmanager.domain.enums.TaskPriority;
import com.taskmanager.domain.enums.TaskStatus;
import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository; // Falsificación del repositorio

    @InjectMocks
    private TaskServiceImpl taskService; // Inyecta el mock dentro del servicio real

    private Task sampleTask;
    private TaskRequestDTO sampleRequestDTO;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de CADA prueba para preparar datos limpios
        sampleTask = Task.builder()
                .id(1L)
                .title("Aprender Pruebas Unitarias")
                .description("Dominar JUnit 5 y Mockito en Spring Boot")
                .status(TaskStatus.EN_PROCESO)
                .priority(TaskPriority.ALTA)
                .createdAt(LocalDateTime.now())
                .build();

        sampleRequestDTO = TaskRequestDTO.builder()
                .title("Aprender Pruebas Unitarias")
                .description("Dominar JUnit 5 y Mockito en Spring Boot")
                .status(TaskStatus.EN_PROCESO)
                .priority(TaskPriority.ALTA)
                .build();
    }

    @Test
    @DisplayName("Debe crear una tarea exitosamente y retornar su DTO")
    void shouldCreateTaskSuccessfully() {
        // GIVEN (Dado que el repositorio guarda cualquier tarea y devuelve sampleTask)
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // WHEN (Cuando llamamos al método del servicio)
        TaskResponseDTO result = taskService.createTask(sampleRequestDTO);

        // THEN (Entonces verificamos los resultados)
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Aprender Pruebas Unitarias", result.getTitle());
        assertEquals(TaskStatus.EN_PROCESO, result.getStatus());

        // Verificamos que el repositorio fue llamado exactamente 1 vez
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Debe buscar una tarea por ID existente y retornar su DTO")
    void shouldFindTaskByIdWhenExists() {
        // GIVEN
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        // WHEN
        TaskResponseDTO result = taskService.getTaskById(1L);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Aprender Pruebas Unitarias", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el ID no existe")
    void shouldThrowExceptionWhenTaskNotFound() {
        // GIVEN (El repositorio no encuentra nada para el ID 99)
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN & THEN (Verificamos que lance la excepción esperada)
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTaskById(99L);
        });

        // Verificamos que se intentó buscar el ID 99
        verify(taskRepository, times(1)).findById(99L);
    }
}