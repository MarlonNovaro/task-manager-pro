package com.taskmanager.config;

import com.taskmanager.domain.entity.Task;
import com.taskmanager.domain.enums.TaskPriority;
import com.taskmanager.domain.enums.TaskStatus;
import com.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) {
        // Solo sembramos datos si la tabla de tareas está totalmente vacía
        if (taskRepository.count() == 0) {
            List<Task> initialTasks = List.of(
                    Task.builder()
                            .title("Configuración de Servidor de Base de Datos")
                            .description("Instalar y parametrizar MySQL 8.x en entorno corporativo")
                            .status(TaskStatus.COMPLETADA)
                            .priority(TaskPriority.ALTA)
                            .dueDate(LocalDateTime.now().plusDays(2))
                            .build(),
                    Task.builder()
                            .title("Desarrollo de API REST con Spring Boot")
                            .description("Construir capa de controladores, servicios y validaciones Jakarta")
                            .status(TaskStatus.EN_PROCESO)
                            .priority(TaskPriority.URGENTE)
                            .dueDate(LocalDateTime.now().plusDays(5))
                            .build(),
                    Task.builder()
                            .title("Diseño de Interfaz en Angular Material")
                            .description("Construir tabla reactiva con filtros de estado y ordenamiento")
                            .status(TaskStatus.PENDIENTE)
                            .priority(TaskPriority.MEDIA)
                            .dueDate(LocalDateTime.now().plusDays(10))
                            .build());

            taskRepository.saveAll(initialTasks);
            System.out.println(">>> [DataInitializer] Se insertaron 3 tareas de demostración exitosamente.");
        }
    }
}