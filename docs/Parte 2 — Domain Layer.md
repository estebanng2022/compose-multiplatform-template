
---

## 🧠 Parte 2 — Domain Layer

### 🎯 Objetivo

Definir la **lógica central** del sistema Ai Factory:
modelos, entidades y casos de uso que representan cómo funciona la “fábrica”.

---

### 📁 Estructura recomendada

```
shared/domain/
  models/
    Agent.kt
    RuleSet.kt
    Task.kt
    Pipeline.kt
    Run.kt
    Artifact.kt
  usecases/
    RegisterAgent.kt
    ValidateRuleSet.kt
    ExecutePipeline.kt
    MonitorRun.kt
    AuditHistory.kt
```

---

### ⚙️ Modelos clave

| Modelo       | Descripción                                                        |
| ------------ | ------------------------------------------------------------------ |
| **Agent**    | Representa un AI local con su rol, reglas y herramientas.          |
| **RuleSet**  | Define el archivo de reglas asociado a un Agent o Pipeline.        |
| **Task**     | Unidad de trabajo (crear archivo, compilar, generar código, etc.). |
| **Pipeline** | Secuencia de Tasks que produce un resultado.                       |
| **Run**      | Ejecución de un Pipeline con estado, logs y tiempo.                |
| **Artifact** | Resultado tangible (ZIP, snapshot, log, build, etc.).              |

---

### ⚙️ Casos de uso principales

| Caso de uso         | Propósito                                                 |
| ------------------- | --------------------------------------------------------- |
| **RegisterAgent**   | Registrar un nuevo AI leyendo `configs/agents/*.yaml`.    |
| **ValidateRuleSet** | Validar la estructura y checksum del archivo de reglas.   |
| **ExecutePipeline** | Ejecutar los pasos definidos en un pipeline.              |
| **MonitorRun**      | Emitir logs y estado en tiempo real durante la ejecución. |
| **AuditHistory**    | Guardar y comparar resultados anteriores (diffs).         |

---

### 🧩 Reglas de diseño

* Solo depende de la capa **Core** (no de Data ni UI).
* No incluye detalles de almacenamiento ni APIs.
* Cada caso de uso debe ser una clase pura, fácil de testear.
* Todas las entidades deben tener `id`, `createdAt`, `updatedAt`.
* La comunicación entre capas usa `Result` o `Either` del Core.

---

### ✅ Definición de Hecho

* [x] Carpeta `shared/domain/` creada.
* [x] Todos los modelos base definidos.
* [x] Casos de uso implementados y testeables.
* [x] Validación de reglas (`RuleSet`) funcional.
* [x] Integración básica con Core (`Result`, `Logger`).

---

> **Nota de Análisis:** Este documento fue revisado y verificado por el asistente de IA el 24 de mayo de 2024. Se crearon los directorios, modelos y casos de uso correspondientes.
