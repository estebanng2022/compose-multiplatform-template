# Ai Factory — Blueprint v2

> **Visión General**
> Ai Factory será una plataforma local para **crear apps nativas premium por capas**, 100% offline, controlada por AIs locales.
> Todo será **editable** (reglas, pipelines, themes, widgets), excepto el **núcleo base protegido**.

---

## 🧠 Parte 1 — Núcleo & Orquestación de AIs

### 1) Objetivo

Controlar AIs locales, ver su estado, editar reglas y lanzar pipelines para crear proyectos por capas.

### 2) Capas (arquitectura)

* **Core (protegido)** → Tipos base (Result, Id, Clock), config, logging, file IO.
* **Domain** → Modelos + casos de uso (Agents, Pipelines, Rules, Artifacts).
* **Data** → Repositorios (fs/json/yaml), adaptadores a procesos externos.
* **Presentation** → ViewModels, validaciones, acciones.
* **UI** → Pantallas y widgets editables.

### 3) Módulos (monorepo)

```
shared/        ← Core, Domain, Data, Presentation
app-android/   ← UI nativa Android
app-desktop/   ← UI opcional Desktop Compose
tools/cli/     ← Comandos
configs/agents/← Perfiles de AIs locales (yaml/json)
artifacts/     ← Salidas: logs, builds, snapshots
```

### 4) Modelos clave

`Agent`, `RuleSet`, `Task`, `Pipeline`, `Run`, `Artifact`

### 5) Flujos

Registrar AI → Plan → Ejecutar → Monitorear → Auditar.

### 6) UI (mínimo)

Dashboard, Agents, Pipelines, Runs, Settings.

### 7) CLI

```
agents scan --dir configs/agents
pipelines list
run start --pipeline <id>
run logs --id <runId>
```

### 8) Núcleo protegido (no editable)

* Estructura base de carpetas.
* Módulos principales (`shared`, `app-android`, `tools`).
* Tipos de Core (`Result`, `Id`, etc.).

---

## 🎨 Parte 2 — Design System & Themes

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
Editable desde Settings → sección **Design System**.

### Futura App Showcase

Una app de muestra integrada para visualizar todos los themes y widgets.
Servirá como catálogo visual de la marca Ai Factory.

---

## ⚙️ Parte 3 — Pipelines y Creación por Capas

### Objetivo

Permitir crear proyectos nativos completos con un clic, siguiendo el blueprint.

### Características

* Pipelines prearmados por capas (Core → Domain → Data → UI).
* 100% editables desde Settings → sección **Projects**.
* Reglas de cada paso definidas en archivos `.yaml`.

### Ejemplo pipeline

```
1️⃣ Crear estructura del proyecto
2️⃣ Generar archivos base
3️⃣ Configurar dependencias
4️⃣ Compilar y probar
5️⃣ Empaquetar y registrar build
```

### Modo de uso

Desde Ai Factory → seleccionar pipeline → verificar → “Run”.

---

## 🧩 Parte 4 — Validadores Premium

Incluye validadores automáticos para mantener la calidad:

* a11y (accesibilidad)
* tokens (espaciado, color, padding)
* snapshots (UI)
* api-surface (contratos)

---

## 📦 Parte 5 — Empaquetado y Portabilidad

* Exportar proyectos listos para Android y Desktop Compose.
* Guardar builds, logs y backups en `artifacts/`.
* Portable: mover carpeta Ai Factory a otra PC sin perder nada.

---

## 💬 Parte 6 — Preguntas Fijas por Screen

Cada pantalla se evalúa con una lista fija de preguntas (para AI o humano):
1️⃣ ¿La pantalla cumple el objetivo definido?
2️⃣ ¿Usa correctamente el layout y los widgets del Design System?
3️⃣ ¿El flujo de datos respeta las capas (Presentation → Domain → Data)?
4️⃣ ¿Tiene estados claros (loading, empty, error)?
5️⃣ ¿Cumple reglas de accesibilidad y theme?
6️⃣ ¿Se puede reutilizar o mejorar algo?

Estas preguntas siempre son las mismas; un AI puede leerlas, analizarlas y guiar mejoras.

---

## ✅ Definición de Hecho Global

* Todo editable desde Settings (reglas, pipelines, themes, widgets).
* Núcleo base protegido (no editable).
* Pipelines funcionales y prearmados.
* Design System con múltiples themes listos.
* App Showcase creada.
* Lista fija de preguntas activa para revisión por pantalla.

- Ver tambi�n: Parte 7-13 - Desktop Only.md (Showcase, Plugins, Packaging, QA, DoD)
