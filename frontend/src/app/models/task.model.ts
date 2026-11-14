export type TaskStatus = 'PENDIENTE' | 'EN_PROCESO' | 'COMPLETADA' | 'CANCELADA';

export type TaskPriority = 'BAJA' | 'MEDIA' | 'ALTA' | 'URGENTE';

export interface Task {
    id?: number;
    title: string;
    description?: string;
    status: TaskStatus;
    priority: TaskPriority;
    dueDate?: string;
    createdAt?: string;
    updatedAt?: string;
}

export interface TaskRequest {
    title: string;
    description?: string;
    status?: TaskStatus;
    priority: TaskPriority;
    dueDate?: string;
}