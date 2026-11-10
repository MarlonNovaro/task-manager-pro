package com.taskmanager.repository;

import com.taskmanager.domain.entity.Task;
import com.taskmanager.domain.enums.TaskPriority;
import com.taskmanager.domain.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Métodos de consulta derivados (Query Methods)
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);

    List<Task> findByTitleContainingIgnoreCase(String title);
}