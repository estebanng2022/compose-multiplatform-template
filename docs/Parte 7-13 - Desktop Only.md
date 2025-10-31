# Parte 7–13 — Desktop Only (Showcase, Policies, Plugins, IO, Packaging, QA, DoD)

> Guía de trabajo para cerrar las partes 7 a 13 enfocadas en Desktop.
> Se usa como checklist: no avanzar hasta cumplir verificación de cada parte.

## Parte 7 — Showcase + AI Integration (Desktop)

- Objetivo
  - Exponer un Showcase navegable en Desktop con demos mínimas y hooks a AI donde aplique.
- Acciones
  - Añadir ruta `Routes.SHOWCASE` y pantalla `ShowcaseApp` (si no existe) con secciones: Buttons, Chips, Cards, States.
  - Conectar ejemplos a datos simulados y a validadores (cuando corresponda) para ver estados reales.
  - Atajos de teclado y A11y básicos en componentes de showcase.
- Verificación
  - Navegación al Showcase desde SideNav o ruta profunda funciona en Desktop.
  - El Showcase se ejecuta sin dependencias de Android.
- Checklist
  - [ ] Ruta y entrada de menú hacia Showcase disponible en Desktop
  - [ ] Secciones demo visibles y navegables
  - [ ] Ejemplos cubren estados (default/hover/disabled/loading/error)

## Parte 8 — Policy & Settings (Desktop)

- Objetivo
  - Preferencias y políticas locales para Desktop: tema, accesibilidad, telemetría, etc.
- Acciones
  - Persistir settings en filesystem (ver `shared/src/jvmMain/kotlin/aifactory/core/JvmFileIO.kt`).
  - Controles en UI Desktop (toggles) y lectura/escritura vía capa de settings.
  - A11y mínimos: roles ARIA, focus, announcements (no bloquear si no están disponibles).
- Verificación
  - Cambios en settings persisten entre ejecuciones Desktop.
  - No rompe Android/iOS (settings deben ser opcionales o con defaults).
- Checklist
  - [ ] Modelo de settings común (con defaults)
  - [ ] Persistencia Desktop funcional
  - [ ] Controles en UI y lectura de estado

## Parte 9 — Plugin System (Desktop)

- Objetivo
  - Descubrir y cargar “plugins” de forma local (sin red) para extender el Showcase/Tools.
- Acciones
  - Definir contrato simple (p.ej. `PluginDescriptor` con `id`, `name`, `entry`, `type`).
  - Descubrir en `configs/plugins/*.yaml` y validar schema mínimo.
  - Exponer lista en UI y permitir activar/desactivar.
- Verificación
  - Plugins válidos aparecen y se pueden activar; inválidos generan error legible.
- Checklist
  - [ ] Contrato de plugin definido
  - [ ] Loader (filesystem) con validación
  - [ ] UI de listado/estado

## Parte 10 — File I/O Local (Desktop)

- Objetivo
  - Operaciones de lectura/escritura/borrado locales para Desktop (sin depender de Android APIs).
- Acciones
  - Usar `JvmFileIO` para `read/write/delete/exists`.
  - Normalizar paths relativos bajo directorio de trabajo de Desktop.
  - Añadir pruebas ligeras (si procede) o un comando CLI dev (`tools/cli`).
- Verificación
  - Escribir/leer YAML en `shared/ui/themes` y `configs/**` funciona en Desktop.
- Checklist
  - [ ] Abstracción de IO usada por Desktop
  - [ ] Paths y errores controlados

## Parte 11 — Packaging (Desktop)

- Objetivo
  - Generar artefactos Desktop reproducibles (zip/installer) sin tocar UI.
- Acciones
  - Reutilizar utilidades en `tools/packaging/DesktopPackager.kt`.
  - Agregar tarea Gradle o script que empaquete binarios y assets (+ README).
- Verificación
  - Artefacto generado con versión y hash; arranca Showcase.
- Checklist
  - [ ] Script/tarea de empaquetado
  - [ ] Artefacto con metadatos y assets

## Parte 12 — QA Gates (Desktop)

- Objetivo
  - Validaciones mínimas antes de empaquetar.
- Acciones
  - Reusar validadores en `tools/validators/*`: tokens, accesibilidad, snapshot/API surface si aplican.
  - Wirear una tarea Gradle/CLI que ejecute validadores y falle en caso de error.
- Verificación
  - Gate falla con tokens inválidos o accesibilidad deficiente.
- Checklist
  - [ ] TokenValidator
  - [ ] AccessibilityValidator
  - [ ] ApiSurface/Snapshot (si corresponde)

## Parte 13 — Definition of Done (Desktop)

- [ ] Showcase Desktop accesible desde la app
- [ ] Settings/Policies persisten (Desktop) sin romper otras plataformas
- [ ] Sistema de plugins local (descubrimiento + UI)
- [ ] File IO Desktop estable para YAML y assets
- [ ] Packaging Desktop scriptable y reproducible
- [ ] QA Gates conectados al empaquetado
- [ ] Build Desktop y Android sin errores de paquetes/imports

---

### Notas de Integración

- Validadores y Packaging: ver carpeta `tools/` existente en el repo.
- Tokens y Temas: semillas en `shared/src/commonMain/kotlin/aifactory/ui/themes/seeds/**/theme.yaml`.
- Mantener cambios de Desktop detrás de features/config para no impactar Android.