import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Task, TaskPriority, TaskStatus } from '../../models/task.model';
import { TaskService } from '../../services/task.service';

// Importaciones de Angular Material
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
    selector: 'app-task-dashboard',
    standalone: true,
    imports: [
        CommonModule,
        MatToolbarModule,
        MatCardModule,
        MatTableModule,
        MatButtonModule,
        MatIconModule,
        MatChipsModule,
        MatFormFieldModule,
        MatSelectModule,
        MatSnackBarModule,
        MatProgressSpinnerModule
    ],
    templateUrl: './task-dashboard.component.html',
    styleUrl: './task-dashboard.component.scss'
})
export class TaskDashboardComponent implements OnInit {

    tasks: Task[] = [];
    loading = false;
    selectedStatus: TaskStatus | 'TODAS' = 'TODAS';

    // Columnas que se mostrarán en la tabla de Angular Material
    displayedColumns: string[] = ['id', 'title', 'priority', 'status', 'dueDate', 'actions'];

    constructor(
        private taskService: TaskService,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit(): void {
        this.loadTasks();
    }

    loadTasks(): void {
        this.loading = true;
        const filter = this.selectedStatus === 'TODAS' ? undefined : this.selectedStatus;

        this.taskService.getTasks(filter).subscribe({
            next: (data) => {
                this.tasks = data;
                this.loading = false;
            },
            error: (err) => {
                this.loading = false;
                this.showToast('Error al conectar con el servidor Spring Boot', 'error');
            }
        });
    }

    onFilterChange(status: TaskStatus | 'TODAS'): void {
        this.selectedStatus = status;
        this.loadTasks();
    }

    deleteTask(id: number): void {
        if (confirm('¿Estás seguro de que deseas eliminar esta tarea?')) {
            this.taskService.deleteTask(id).subscribe({
                next: () => {
                    this.showToast('Tarea eliminada correctamente', 'success');
                    this.loadTasks();
                },
                error: () => this.showToast('Error al intentar eliminar la tarea', 'error')
            });
        }
    }

    // Cálculos reactivos para las tarjetas de estadísticas
    get totalCount(): number { return this.tasks.length; }
    get pendingCount(): number { return this.tasks.filter(t => t.status === 'PENDIENTE').length; }
    get inProgressCount(): number { return this.tasks.filter(t => t.status === 'EN_PROCESO').length; }
    get completedCount(): number { return this.tasks.filter(t => t.status === 'COMPLETADA').length; }

    // Métodos auxiliares para colores de Chips
    getPriorityClass(priority: TaskPriority): string {
        switch (priority) {
            case 'URGENTE': return 'chip-urgent';
            case 'ALTA': return 'chip-high';
            case 'MEDIA': return 'chip-medium';
            case 'BAJA': return 'chip-low';
            default: return '';
        }
    }

    getStatusClass(status: TaskStatus): string {
        switch (status) {
            case 'COMPLETADA': return 'chip-completed';
            case 'EN_PROCESO': return 'chip-progress';
            case 'PENDIENTE': return 'chip-pending';
            case 'CANCELADA': return 'chip-cancelled';
            default: return '';
        }
    }

    private showToast(message: string, type: 'success' | 'error'): void {
        this.snackBar.open(message, 'Cerrar', {
            duration: 3500,
            panelClass: type === 'success' ? 'toast-success' : 'toast-error'
        });
    }
}