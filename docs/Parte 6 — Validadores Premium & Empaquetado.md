
---

## ðŸ§ª Parte 6 â€” Validadores Premium & Empaquetado

### ðŸŽ¯ Objetivo

Garantizar que **cada proyecto y pantalla cumpla el estÃ¡ndar â€œpremiumâ€** de Ai Factory,
y preparar el sistema para empaquetar builds portables (Desktop).

---

### ðŸ“ Estructura recomendada

```
tools/validators/
  AccessibilityValidator.kt
  TokenValidator.kt
  SnapshotValidator.kt
  ApiSurfaceValidator.kt
tools/packaging/
  DesktopPackager.kt
  ZipExporter.kt
```

---

### ðŸ§© Validadores Premium

| Validador                  | PropÃ³sito                                                                                  |
| -------------------------- | ------------------------------------------------------------------------------------------ |
| **AccessibilityValidator** | Verifica que cada pantalla cumpla con a11y (etiquetas, foco, contraste).                   |
| **TokenValidator**         | Comprueba el uso correcto de `Spacing`, `ColorTokens`, `Typography` (sin â€œmagic numbersâ€). |
| **SnapshotValidator**      | Captura imÃ¡genes automÃ¡ticas de UI para comparar entre builds (golden tests).              |
| **ApiSurfaceValidator**    | Detecta cambios en funciones pÃºblicas (rompimiento de contratos).                          |

Todos estos validadores se ejecutan automÃ¡ticamente en cada pipeline o pueden lanzarse desde el CLI.

---

### ðŸ§° CLI (herramientas)

```
tools/cli/
  validate          â† Ejecuta todos los validadores
  snapshots record  â† Graba snapshots base
  snapshots verify  â† Compara snapshots actuales vs golden
  a11y check        â† Revisa accesibilidad mÃ­nima
  api-surface check â† Verifica firmas pÃºblicas
```

---

### ðŸ“¦ Empaquetado & Portabilidad

| Componente          | DescripciÃ³n                                                           |
| ------------------- | --------------------------------------------------------------------- |
| **DesktopPackager** | Genera ejecutables (Windows `.exe`, macOS `.app`, Linux `.AppImage`). |
| **ZipExporter**     | Exporta proyectos completos o backups a `/artifacts/exports/`.        |

---

### ðŸ§± Flujo de empaquetado

```
Run â†’ Validadores (a11y, tokens, snapshots)
   â†“
Si todo OK â†’ Build Desktop
   â†“
Export a artifacts/
```

---

### ðŸ§© Reglas de diseÃ±o

* Validadores y empaquetadores se ejecutan como pasos del pipeline.
* Todo se controla desde *Settings â†’ Validation & Build*.
* Resultados almacenados en `/artifacts/logs/` y `/artifacts/exports/`.
* Si falla un validador crÃ­tico â†’ el pipeline se detiene.

---

### âœ… DefiniciÃ³n de Hecho

* [x] Carpeta `tools/validators/` y `tools/packaging/` creadas.
* [x] CLI funcional (`validate`, `snapshots`, `a11y`, `api-surface`).
* [x] Validadores ejecutan correctamente sobre UI.
* [x] Empaquetado Desktop funcionando.
* [x] Export a ZIP y registros en `artifacts/`.

---

