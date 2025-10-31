﻿# Ai Factory â€” Blueprint v2

> **VisiÃ³n General**
> Ai Factory serÃ¡ una plataforma local para **crear apps nativas premium por capas**, 100% offline, controlada por AIs locales.
> Todo serÃ¡ **editable** (reglas, pipelines, themes, widgets), excepto el **nÃºcleo base protegido**.

---

## ðŸ§  Parte 1 â€” NÃºcleo & OrquestaciÃ³n de AIs

### 1) Objetivo

Controlar AIs locales, ver su estado, editar reglas y lanzar pipelines para crear proyectos por capas.

### 2) Capas (arquitectura)

* **Core (protegido)** â†’ Tipos base (Result, Id, Clock), config, logging, file IO.
* **Domain** â†’ Modelos + casos de uso (Agents, Pipelines, Rules, Artifacts).
* **Data** â†’ Repositorios (fs/json/yaml), adaptadores a procesos externos.
* **Presentation** â†’ ViewModels, validaciones, acciones.
* **UI** â†’ Pantallas y widgets editables.

### 3) MÃ³dulos (monorepo)

```
shared/        â† Core, Domain, Data, Presentation
app-desktop/   â† UI opcional Desktop Compose
tools/cli/     â† Comandos
configs/agents/â† Perfiles de AIs locales (yaml/json)
artifacts/     â† Salidas: logs, builds, snapshots
```

### 4) Modelos clave

`Agent`, `RuleSet`, `Task`, `Pipeline`, `Run`, `Artifact`

### 5) Flujos

Registrar AI â†’ Plan â†’ Ejecutar â†’ Monitorear â†’ Auditar.

### 6) UI (mÃ­nimo)

Dashboard, Agents, Pipelines, Runs, Settings.

### 7) CLI

```
agents scan --dir configs/agents
pipelines list
run start --pipeline <id>
run logs --id <runId>
```

### 8) NÃºcleo protegido (no editable)

* Estructura base de carpetas.
* MÃ³dulos principales (`shared`, `app-desktop`, `tools`).
* Tipos de Core (`Result`, `Id`, etc.).

---

## ðŸŽ¨ Parte 2 â€” Design System & Themes

### Objetivo

Crear un sistema visual modular y de marca, reutilizable entre proyectos.

### Estructura recomendada

```
shared/ui/
  designsystem/
    theme/
      ColorTokens.kt
      Typography.kt
      Shapes.kt
      Spacing.kt
      Sizing.kt
      Theme.kt
    components/
      buttons/
      cards/
      inputs/
      feedback/
      navigation/
    layout/
      ContentView.kt
      PageContainer.kt
      PageHeader.kt
  themes/
    premium/
    minimal/
    animalita/
```

Cada theme es un paquete visual completo.
Editable desde Settings â†’ secciÃ³n **Design System**.

### Futura App Showcase

Una app de muestra integrada para visualizar todos los themes y widgets.
ServirÃ¡ como catÃ¡logo visual de la marca Ai Factory.

---

## âš™ï¸ Parte 3 â€” Pipelines y CreaciÃ³n por Capas

### Objetivo

Permitir crear proyectos nativos completos con un clic, siguiendo el blueprint.

### CaracterÃ­sticas

* Pipelines prearmados por capas (Core â†’ Domain â†’ Data â†’ UI).
* 100% editables desde Settings â†’ secciÃ³n **Projects**.
* Reglas de cada paso definidas en archivos `.yaml`.

### Ejemplo pipeline

```
1ï¸âƒ£ Crear estructura del proyecto
2ï¸âƒ£ Generar archivos base
3ï¸âƒ£ Configurar dependencias
4ï¸âƒ£ Compilar y probar
5ï¸âƒ£ Empaquetar y registrar build
```

### Modo de uso

Desde Ai Factory â†’ seleccionar pipeline â†’ verificar â†’ â€œRunâ€.

---

## ðŸ§© Parte 4 â€” Validadores Premium

Incluye validadores automÃ¡ticos para mantener la calidad:

* a11y (accesibilidad)
* tokens (espaciado, color, padding)
* snapshots (UI)
* api-surface (contratos)

---

## ðŸ“¦ Parte 5 â€” Empaquetado y Portabilidad

* Exportar proyectos listos para Desktop Compose.
* Guardar builds, logs y backups en `artifacts/`.
* Portable: mover carpeta Ai Factory a otra PC sin perder nada.

---

## ðŸ’¬ Parte 6 â€” Preguntas Fijas por Screen

Cada pantalla se evalÃºa con una lista fija de preguntas (para AI o humano):
1ï¸âƒ£ Â¿La pantalla cumple el objetivo definido?
2ï¸âƒ£ Â¿Usa correctamente el layout y los widgets del Design System?
3ï¸âƒ£ Â¿El flujo de datos respeta las capas (Presentation â†’ Domain â†’ Data)?
4ï¸âƒ£ Â¿Tiene estados claros (loading, empty, error)?
5ï¸âƒ£ Â¿Cumple reglas de accesibilidad y theme?
6ï¸âƒ£ Â¿Se puede reutilizar o mejorar algo?

Estas preguntas siempre son las mismas; un AI puede leerlas, analizarlas y guiar mejoras.

---

## âœ… DefiniciÃ³n de Hecho Global

* Todo editable desde Settings (reglas, pipelines, themes, widgets).
* NÃºcleo base protegido (no editable).
* Pipelines funcionales y prearmados.
* Design System con mÃºltiples themes listos.
* App Showcase creada.
* Lista fija de preguntas activa para revisiÃ³n por pantalla.

- Ver también: Parte 7-13 - Desktop Only.md (Showcase, Plugins, Packaging, QA, DoD)

