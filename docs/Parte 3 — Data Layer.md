
---

## 💾 Parte 3 — Data Layer

### 🎯 Objetivo

Gestionar toda la **persistencia y acceso a datos** de Ai Factory:
archivos locales, configuraciones, logs y adaptadores externos.
Es la capa que conecta la lógica del **Domain** con las fuentes reales (FS, JSON, YAML, CLI, etc.).

---

### 📁 Estructura recomendada

```
shared/data/
  repositories/
    AgentRepository.kt
    RuleSetRepository.kt
    PipelineRepository.kt
    RunRepository.kt
    ArtifactRepository.kt
  datasources/
    LocalFileDataSource.kt
    YamlDataSource.kt
    JsonDataSource.kt
    CliDataSource.kt
```

---

### ⚙️ Repositorios principales

| Repositorio            | Propósito                                                    |
| ---------------------- | ------------------------------------------------------------ |
| **AgentRepository**    | Leer y guardar datos de `configs/agents/*.yaml`.             |
| **RuleSetRepository**  | Cargar, guardar y validar los archivos de reglas.            |
| **PipelineRepository** | Gestionar pipelines (listado, lectura y definición).         |
| **RunRepository**      | Registrar y actualizar el estado de cada ejecución.          |
| **ArtifactRepository** | Guardar o listar los artefactos generados (ZIP, logs, etc.). |

---

### ⚙️ Fuentes de datos

| Fuente                  | Descripción                                             |
| ----------------------- | ------------------------------------------------------- |
| **LocalFileDataSource** | Interfaz genérica para lectura/escritura de archivos.   |
| **YamlDataSource**      | Parser para archivos `.yaml` (agentes, reglas).         |
| **JsonDataSource**      | Parser para datos estructurados (logs, pipelines).      |
| **CliDataSource**       | Adaptador para ejecutar comandos externos o AI locales. |

---

### 🧩 Reglas de diseño

* Solo depende del **Core** y del **Domain**.
* Toda operación debe devolver un `Result` (sin excepciones directas).
* No debe incluir lógica de presentación ni UI.
* Los paths deben provenir de `Config.kt` (en Core).
* Los logs se registran con el `Logger` central.
* Soporte para múltiples formatos (`.yaml`, `.json`, `.txt`).

---

### ⚙️ Ejemplo de flujo

1️⃣ `Domain` pide a `PipelineRepository` listar pipelines.
2️⃣ Este usa `YamlDataSource` o `JsonDataSource` para leer archivos.
3️⃣ Los datos se validan con modelos `Pipeline`.
4️⃣ El resultado se devuelve al `Presentation` como `Result<List<Pipeline>>`.

---

### ✅ Definición de Hecho

* [x] Carpeta `shared/data/` creada.
* [x] Repositorios implementados y probados con mocks.
* [x] Lectura de `configs/agents` funcional.
* [x] Soporte completo para JSON y YAML.
* [x] Integración de logs (`Logger`) y resultados (`Result`).

---
