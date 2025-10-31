# Status — Desktop Only (Partes 7–13)

Resumen rápido del avance y pendientes para la migración Desktop‑only.

## Parte 7 — Showcase + AI Integration (Desktop)
- [ ] Ruta/entrada Showcase en Desktop
- [ ] Secciones demo visibles (Buttons/Chips/Cards/States)
- [ ] Estados cubiertos (default/hover/disabled/loading/error)

## Parte 8 — Policy & Settings (Desktop)
- [ ] Modelo de settings común (con defaults)
- [ ] Persistencia Desktop (leer/escribir con File IO)
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
- Entry point Desktop ajustado (`desktopApp/build.gradle.kts` → `mainClass = "MainKt"`).
- Ejecución local: `./gradlew :desktopApp:run`.

