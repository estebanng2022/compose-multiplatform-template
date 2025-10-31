# AI Factory Showcase

La implementación del Showcase Android vive dentro del módulo principal (`androidApp/src/main/java/aifactory/showcase`). Aquí se centralizan las pantallas y la navegación descritas en la Parte 7 del manual:

* `ShowcaseApp` y `ShowcaseNavHost` exponen pestañas para **Themes**, **Components**, **Pipeline demo** y **AI Agents**.
* Las pantallas en `androidApp/src/main/java/aifactory/showcase/screens/` renderizan los ejemplos visuales y los datos simulados.
* El NavGraph principal (`androidApp/src/main/java/aifactory/navigation/NavGraph.kt`) incluye la ruta `Routes.SHOWCASE`, accesible desde la SideNav.

Los recursos de agentes (`configs/agents/*.yaml`) y reglas (`configs/rules/*.yaml`) se cargan mediante `LocalAIManager`, ubicado en `shared/src/commonMain/kotlin/aifactory/ai/`.

