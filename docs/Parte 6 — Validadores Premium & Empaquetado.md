
---

## 🧪 Parte 6 — Validadores Premium & Empaquetado

### 🎯 Objetivo

Garantizar que **cada proyecto y pantalla cumpla el estándar “premium”** de Ai Factory,
y preparar el sistema para empaquetar builds portables (Android + Desktop).

---

### 📁 Estructura recomendada

```
tools/validators/
  AccessibilityValidator.kt
  TokenValidator.kt
  SnapshotValidator.kt
  ApiSurfaceValidator.kt
tools/packaging/
  AndroidPackager.kt
  DesktopPackager.kt
  ZipExporter.kt
```

---

### 🧩 Validadores Premium

| Validador                  | Propósito                                                                                  |
| -------------------------- | ------------------------------------------------------------------------------------------ |
| **AccessibilityValidator** | Verifica que cada pantalla cumpla con a11y (etiquetas, foco, contraste).                   |
| **TokenValidator**         | Comprueba el uso correcto de `Spacing`, `ColorTokens`, `Typography` (sin “magic numbers”). |
| **SnapshotValidator**      | Captura imágenes automáticas de UI para comparar entre builds (golden tests).              |
| **ApiSurfaceValidator**    | Detecta cambios en funciones públicas (rompimiento de contratos).                          |

Todos estos validadores se ejecutan automáticamente en cada pipeline o pueden lanzarse desde el CLI.

---

### 🧰 CLI (herramientas)

```
tools/cli/
  validate          ← Ejecuta todos los validadores
  snapshots record  ← Graba snapshots base
  snapshots verify  ← Compara snapshots actuales vs golden
  a11y check        ← Revisa accesibilidad mínima
  api-surface check ← Verifica firmas públicas
```

---

### 📦 Empaquetado & Portabilidad

| Componente          | Descripción                                                           |
| ------------------- | --------------------------------------------------------------------- |
| **AndroidPackager** | Crea APK/AAB firmados listos para distribución.                       |
| **DesktopPackager** | Genera ejecutables (Windows `.exe`, macOS `.app`, Linux `.AppImage`). |
| **ZipExporter**     | Exporta proyectos completos o backups a `/artifacts/exports/`.        |

---

### 🧱 Flujo de empaquetado

```
Run → Validadores (a11y, tokens, snapshots)
   ↓
Si todo OK → Build Android / Desktop
   ↓
Export a artifacts/
```

---

### 🧩 Reglas de diseño

* Validadores y empaquetadores se ejecutan como pasos del pipeline.
* Todo se controla desde *Settings → Validation & Build*.
* Resultados almacenados en `/artifacts/logs/` y `/artifacts/exports/`.
* Si falla un validador crítico → el pipeline se detiene.

---

### ✅ Definición de Hecho

* [x] Carpeta `tools/validators/` y `tools/packaging/` creadas.
* [x] CLI funcional (`validate`, `snapshots`, `a11y`, `api-surface`).
* [x] Validadores ejecutan correctamente sobre UI.
* [x] Empaquetado Android + Desktop funcionando.
* [x] Export a ZIP y registros en `artifacts/`.

---
