# Parte 7�13 � Desktop Only (Showcase, Policies, Plugins, IO, Packaging, QA, DoD)

> Gu�a de trabajo para cerrar las partes 7 a 13 enfocadas en Desktop.
> Se usa como checklist: no avanzar hasta cumplir verificaci�n de cada parte.

## Parte 7 � Showcase + AI Integration (Desktop)

- Objetivo
  - Exponer un Showcase navegable en Desktop con demos m�nimas y hooks a AI donde aplique.
- Acciones
  - A�adir ruta `Routes.SHOWCASE` y pantalla `ShowcaseApp` (si no existe) con secciones: Buttons, Chips, Cards, States.
  - Conectar ejemplos a datos simulados y a validadores (cuando corresponda) para ver estados reales.
  - Atajos de teclado y A11y b�sicos en componentes de showcase.
- Verificaci�n
  - Navegaci�n al Showcase desde SideNav o ruta profunda funciona en Desktop.
  - El Showcase se ejecuta sin dependencias de Android.
- Checklist
  - [ ] Ruta y entrada de men� hacia Showcase disponible en Desktop
  - [ ] Secciones demo visibles y navegables
  - [ ] Ejemplos cubren estados (default/hover/disabled/loading/error)

## Parte 8 � Policy & Settings (Desktop)

- Objetivo
  - Preferencias y pol�ticas locales para Desktop: tema, accesibilidad, telemetr�a, etc.
- Acciones
  - Persistir settings en filesystem (ver `shared/src/jvmMain/kotlin/aifactory/core/JvmFileIO.kt`).
  - Controles en UI Desktop (toggles) y lectura/escritura v�a capa de settings.
  - A11y m�nimos: roles ARIA, focus, announcements (no bloquear si no est�n disponibles).
- Verificaci�n
  - Cambios en settings persisten entre ejecuciones Desktop.
  - No rompe Android/iOS (settings deben ser opcionales o con defaults).
- Checklist
  - [ ] Modelo de settings com�n (con defaults)
  - [ ] Persistencia Desktop funcional
  - [ ] Controles en UI y lectura de estado

## Parte 9 � Plugin System (Desktop)

- Objetivo
  - Descubrir y cargar �plugins� de forma local (sin red) para extender el Showcase/Tools.
- Acciones
  - Definir contrato simple (p.ej. `PluginDescriptor` con `id`, `name`, `entry`, `type`).
  - Descubrir en `configs/plugins/*.yaml` y validar schema m�nimo.
  - Exponer lista en UI y permitir activar/desactivar.
- Verificaci�n
  - Plugins v�lidos aparecen y se pueden activar; inv�lidos generan error legible.
- Checklist
  - [ ] Contrato de plugin definido
  - [ ] Loader (filesystem) con validaci�n
  - [ ] UI de listado/estado

## Parte 10 � File I/O Local (Desktop)

- Objetivo
  - Operaciones de lectura/escritura/borrado locales para Desktop (sin depender de Android APIs).
- Acciones
  - Usar `JvmFileIO` para `read/write/delete/exists`.
  - Normalizar paths relativos bajo directorio de trabajo de Desktop.
  - A�adir pruebas ligeras (si procede) o un comando CLI dev (`tools/cli`).
- Verificaci�n
  - Escribir/leer YAML en `shared/ui/themes` y `configs/**` funciona en Desktop.
- Checklist
  - [ ] Abstracci�n de IO usada por Desktop
  - [ ] Paths y errores controlados

## Parte 11 � Packaging (Desktop)

- Objetivo
  - Generar artefactos Desktop reproducibles (zip/installer) sin tocar UI.
- Acciones
  - Reutilizar utilidades en `tools/packaging/DesktopPackager.kt`.
  - Agregar tarea Gradle o script que empaquete binarios y assets (+ README).
- Verificaci�n
  - Artefacto generado con versi�n y hash; arranca Showcase.
- Checklist
  - [ ] Script/tarea de empaquetado
  - [ ] Artefacto con metadatos y assets

## Parte 12 � QA Gates (Desktop)

- Objetivo
  - Validaciones m�nimas antes de empaquetar.
- Acciones
  - Reusar validadores en `tools/validators/*`: tokens, accesibilidad, snapshot/API surface si aplican.
  - Wirear una tarea Gradle/CLI que ejecute validadores y falle en caso de error.
- Verificaci�n
  - Gate falla con tokens inv�lidos o accesibilidad deficiente.
- Checklist
  - [ ] TokenValidator
  - [ ] AccessibilityValidator
  - [ ] ApiSurface/Snapshot (si corresponde)

## Parte 13 � Definition of Done (Desktop)

- [ ] Showcase Desktop accesible desde la app
- [ ] Settings/Policies persisten (Desktop) sin romper otras plataformas
- [ ] Sistema de plugins local (descubrimiento + UI)
- [ ] File IO Desktop estable para YAML y assets
- [ ] Packaging Desktop scriptable y reproducible
- [ ] QA Gates conectados al empaquetado
- [ ] Build Desktop y Android sin errores de paquetes/imports

---

### Notas de Integraci�n

- Validadores y Packaging: ver carpeta `tools/` existente en el repo.
- Tokens y Temas: semillas en `shared/src/commonMain/kotlin/aifactory/ui/themes/seeds/**/theme.yaml`.
- Mantener cambios de Desktop detr�s de features/config para no impactar Android.