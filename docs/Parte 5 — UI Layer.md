
---

## 🎨 Parte 5 — UI Layer

### 🎯 Objetivo

Definir la **interfaz visual** completa de Ai Factory: pantallas, layouts y widgets reutilizables.
Esta capa refleja la identidad visual de la marca (Design System + Themes) y conecta con la capa **Presentation**.

---

### 📁 Estructura recomendada

```
app-android/
  ui/
    screens/
      DashboardScreen.kt
      AgentsScreen.kt
      PipelinesScreen.kt
      RunsScreen.kt
      SettingsScreen.kt
    components/
      SideNavigation.kt
      PageHeader.kt
      ContentView.kt
      StateViews.kt
    theme/
      Theme.kt
      ColorTokens.kt
      Typography.kt
      Shapes.kt
      Spacing.kt
      Sizing.kt
    widgets/
      buttons/
      cards/
      inputs/
      feedback/
      overlays/
    previews/
      ThemePreviews.kt
      ComponentPreviews.kt
```

---

### 🧱 Capas visuales

| Elemento       | Descripción                                                        |
| -------------- | ------------------------------------------------------------------ |
| **Screens**    | Pantallas principales conectadas a los `ViewModels`.               |
| **Components** | Elementos visuales grandes (contenedores, navegación, headers).    |
| **Widgets**    | Pequeños bloques reutilizables (botones, chips, tooltips, inputs). |
| **Theme**      | Define colores, tipografía, tamaños y espaciado de marca.          |
| **Previews**   | Visualización rápida de componentes y themes en IDE.               |

---

### 🧩 Conexión con el Design System

* Todos los widgets usan **tokens** (`Spacing`, `Sizing`, `ColorTokens`).
* Cada pantalla hereda del **Theme activo** (ej. `premium`, `minimal`, `animalita`).
* Los **themes** se seleccionan y editan desde *Settings → Design System*.
* Los cambios en tokens o colores se reflejan automáticamente en toda la app.

---

### 🧠 Flujo UI

```
Presentation (ViewModel)
        ↓
   UI (Screens)
        ↓
Components → Widgets → Theme
```

* Las pantallas leen el estado de `ViewModel`.
* Los componentes muestran contenido según el `UiState`.
* Los widgets y themes garantizan coherencia visual entre proyectos.

---

### 🎨 Design System Ready

Cada nuevo proyecto puede usar:

```
shared/ui/themes/
  premium/
  minimal/
  animalita/
```

Y seleccionar el theme desde *Settings* o por regla del pipeline.

---

### ✅ Definición de Hecho

* [x] Carpeta `app-android/ui/` creada.
* [x] Pantallas principales conectadas a `ViewModels`.
* [x] Widgets reutilizables implementados (según tokens).
* [x] Themes y tokens configurados.
* [x] Previews funcionales para componentes y layouts.
* [x] Integración con la capa Presentation estable.

---
