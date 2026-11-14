import { Component } from '@angular/core';
import { TaskDashboardComponent } from './components/task-dashboard/task-dashboard.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [TaskDashboardComponent],
  template: `<app-task-dashboard></app-task-dashboard>`
})
export class App { }