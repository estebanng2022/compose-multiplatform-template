
---

## 🧩 Parte 4 — Presentation Layer

### 🎯 Objetivo

Controlar el **estado, acciones y validaciones** del sistema.
Es la capa que conecta la lógica de **Domain** con la interfaz **UI**, usando `ViewModels` y estados reactivos.

---

### 📁 Estructura recomendada

```
shared/presentation/
  viewmodels/
    AgentsViewModel.kt
    PipelinesViewModel.kt
    RunsViewModel.kt
    SettingsViewModel.kt
  state/
    UiState.kt
    UiEvent.kt
    UiEffect.kt
  validation/
    RuleValidator.kt
    PipelineValidator.kt
```

---

### ⚙️ Responsabilidades

| Componente             | Propósito                                                            |
| ---------------------- | -------------------------------------------------------------------- |
| **AgentsViewModel**    | Maneja la lista y detalle de AIs (lectura/edición de reglas).        |
| **PipelinesViewModel** | Controla la creación, ejecución y validación de pipelines.           |
| **RunsViewModel**      | Supervisa ejecuciones activas (logs, progreso, resultados).          |
| **SettingsViewModel**  | Administra configuraciones globales (paths, themes, AIs conectados). |
| **UiState**            | Representa el estado actual de la UI (loading, success, error).      |
| **UiEvent / UiEffect** | Canalizan acciones y efectos secundarios.                            |
| **Validators**         | Verifican coherencia de reglas y pasos antes de ejecutar pipelines.  |

---

### 🧠 Flujo de datos

```
UI  →  ViewModel  →  Domain (use case)  →  Data (repo)
 ↑                                        ↓
 └────── UiState / UiEvent / UiEffect ─────┘
```

* La UI nunca accede directamente al `Domain` ni al `Data`.
* Los `ViewModels` son el punto central de comunicación.

---

### 🧩 Reglas de diseño

* Solo depende de **Core** y **Domain**.
* No tiene lógica visual (solo estado y acciones).
* Cada `ViewModel` debe tener su propio `UiState`.
* Los estados deben ser **inmutables** (`copy()` para actualizaciones).
* La comunicación con la UI debe ser **reactiva** (Flow, LiveData o StateFlow).

---

### ✅ Definición de Hecho

* [x] Carpeta `shared/presentation/` creada.
* [x] Todos los `ViewModels` principales definidos.
* [x] Estados (`UiState`, `UiEvent`, `UiEffect`) implementados.
* [x] Validadores funcionales (`RuleValidator`, `PipelineValidator`).
* [x] Integración estable con Domain y Data.

---
