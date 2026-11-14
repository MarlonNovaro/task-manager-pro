import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task, TaskPriority, TaskRequest, TaskStatus } from '../models/task.model';

@Injectable({
    providedIn: 'root' // Singleton: una sola instancia para toda la aplicación
})
export class TaskService {

    // URL base de nuestro backend Spring Boot
    private readonly apiUrl = 'http://localhost:8080/api/tasks';

    constructor(private http: HttpClient) { }

    // 1. Obtener todas las tareas (o filtradas por estado)
    getTasks(status?: TaskStatus): Observable<Task[]> {
        let params = new HttpParams();
        if (status) {
            params = params.set('status', status);
        }
        return this.http.get<Task[]>(this.apiUrl, { params });
    }

    // 2. Obtener una tarea por su ID
    getTaskById(id: number): Observable<Task> {
        return this.http.get<Task>(`${this.apiUrl}/${id}`);
    }

    // 3. Crear una nueva tarea
    createTask(task: TaskRequest): Observable<Task> {
        return this.http.post<Task>(this.apiUrl, task);
    }

    // 4. Actualizar una tarea existente
    updateTask(id: number, task: TaskRequest): Observable<Task> {
        return this.http.put<Task>(`${this.apiUrl}/${id}`, task);
    }

    // 5. Eliminar una tarea por ID
    deleteTask(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}