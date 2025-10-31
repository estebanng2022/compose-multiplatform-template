# Status — Desktop Only (Partes 7–13)

Estado actual para entrar en fase de diseño. Marca lo completado vs. pendiente.

## Parte 7 — Showcase + AI Integration (Desktop)
- [ ] Ruta/entrada Showcase en Desktop
- [ ] Secciones demo visibles (Buttons/Chips/Cards/States)
- [ ] Estados cubiertos (default/hover/disabled/loading/error)

## Parte 8 — Policy & Settings (Desktop)
- [ ] Modelo de settings común (con defaults)
- [ ] Persistencia Desktop (File IO)
- [ ] Controles en UI (toggles) funcionando

## Parte 9 — Plugin System (Desktop)
- [ ] Contrato de plugin definido (id, name, entry, type)
- [ ] Loader por filesystem + validación YAML (configs/plugins/*.yaml)
- [ ] UI de listado/activación

## Parte 10 — File I/O Local (Desktop)
- [x] Abstracción IO usable en Desktop (`shared/src/main/kotlin/aifactory/core/JvmFileIO.kt`)
- [ ] Paths y manejo de errores normalizados

## Parte 11 — Packaging (Desktop)
- [x] Script/tarea de empaquetado (Compose Desktop `createDistributable`)
- [ ] Artefacto con metadatos/hash y assets (README/licencia)

## Parte 12 — QA Gates (Desktop)
- [ ] TokenValidator conectado al build
- [ ] AccessibilityValidator conectado
- [ ] ApiSurface/Snapshot (si aplica)

## Parte 13 — DoD (Desktop)
- [ ] Showcase Desktop accesible desde la app
- [ ] Settings/Policies persisten en Desktop
- [ ] Sistema de plugins local (descubrimiento + UI)
- [x] File IO Desktop estable para YAML y assets (base lista)
- [x] Packaging Desktop scriptable y reproducible (task + CI)
- [ ] QA Gates conectados al empaquetado
- [x] Build Desktop sin errores

---

Notas:
- CI mínimo: `.github/workflows/desktop-assemble.yml` ejecuta `:desktopApp:assemble` en PRs.
- Entry point Desktop: `desktopApp/build.gradle.kts` → `mainClass = "aifactory.desktop.MainKt"`.
- Ejecución local: `./gradlew :desktopApp:run`.

