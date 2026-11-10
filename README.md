# 🚀 Task Manager Pro

Plataforma empresarial para la gestión integral de tareas, flujos de trabajo y prioridades operativas para equipos y organizaciones.

## 🛠️ Stack Tecnológico

### Backend
* **Lenguaje:** Java 21 LTS
* **Framework:** Spring Boot 3.x
* **Persistencia:** Spring Data JPA / Hibernate
* **Base de Datos:** MySQL 8.x
* **Validaciones:** Jakarta Validation
* **Auditoría & Productividad:** Lombok, JPA PrePersist/PreUpdate

### Frontend (En construcción)
* **Framework:** Angular 17+
* **Componentes UI:** Angular Material

---

## 🏛️ Arquitectura del Sistema
Diseñado bajo el patrón de **Arquitectura en Capas Limpia (Clean Layered Architecture)**:
* **Controller:** Endpoints REST y contratos HTTP.
* **DTO:** Aislamiento y validación de entrada/salida (`TaskRequestDTO`, `TaskResponseDTO`).
* **Service:** Reglas de negocio y transacciones (`@Transactional`).
* **Repository:** Consultas optimizadas con Spring Data JPA.
* **Domain:** Entidades (`Task`) y tipos enumerados (`TaskStatus`, `TaskPriority`).

---

## 👨‍💻 Autor
Desarrollado por **Marlon Novaro**

