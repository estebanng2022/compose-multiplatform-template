


## 🧱 Parte 1 — Core Layer

### 🎯 Objetivo

Proveer la base estable y reutilizable de todo Ai Factory.
Gestiona tipos, configuraciones, logs y operaciones esenciales del sistema.

---

### 📁 Estructura recomendada

```
shared/core/
  Result.kt          ← manejo de éxito/error (Success, Failure)
  Either.kt          ← flujos condicionales (Left/Right)
  Id.kt              ← generador universal de IDs
  Clock.kt           ← control de tiempo y fechas
  Config.kt          ← carga y validación de settings (yaml/json)
  Logger.kt          ← registro centralizado (AI + sistema)
  FileIO.kt          ← lectura/escritura segura de archivos
```

---

### ⚙️ Funciones clave

| Archivo       | Propósito                                                               |
| ------------- | ----------------------------------------------------------------------- |
| **Result.kt** | Manejar resultados sin lanzar excepciones.                              |
| **Either.kt** | Representar caminos condicionales (éxito o error).                      |
| **Id.kt**     | Generar IDs únicos para Agents, Runs, Pipelines, etc.                   |
| **Clock.kt**  | Obtener tiempos consistentes en logs y builds.                          |
| **Config.kt** | Leer y validar configuraciones globales desde `/configs/settings.yaml`. |
| **Logger.kt** | Escribir logs estructurados en `/artifacts/logs/`.                      |
| **FileIO.kt** | Abstraer lectura/escritura (json, yaml, txt) de manera segura.          |

---

### 🧩 Reglas de diseño

* No depende de ninguna otra capa.
* Solo usa la librería estándar de Kotlin.
* No contiene UI ni lógica de dominio.
* Debe compilar tanto en Android como Desktop Compose.
* Todas las funciones deben ser puras y seguras (sin efectos ocultos).

---

### ✅ Definición de Hecho

* [x] Carpeta `shared/core/` creada.
* [x] Todos los archivos base implementados.
* [x] Logging y Config funcional.
* [x] Sin dependencias externas ni referencias a UI.
* [x] Tests básicos para `Result`, `Either`, `Config`, y `Logger`.

---

> **Nota de Análisis:** Este documento fue revisado y verificado por el asistente de IA el 24 de mayo de 2024. El proyecto cumple con las reglas aquí descritas después de los ajustes realizados.

