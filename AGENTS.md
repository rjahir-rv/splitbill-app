# Agent Guide: KMP Template

Esencial para agentes que trabajen en este template de Kotlin Multiplatform.

## Arquitectura de un Vistazo

Este proyecto utiliza la arquitectura recomendada para Compose Multiplatform con AGP 9.0+, separando la lógica de Android de la lógica compartida.

- **`:kmp`**: Módulo central. Contiene código compartido y UI en Compose.
  - Usa `com.android.kotlin.multiplatform.library`.
  - `src/commonMain`: UI compartida (`App.kt`), modelos y lógica.
  - Ejemplo: `kmp/src/commonMain/kotlin/com/splitbill/kmp/App.kt`.
- **`:androidApp`**: Aplicación Android "pura".
  - Depende de `:kmp`.
  - Configura el `applicationId` y el punto de entrada de Android.
  - Ejemplo: `androidApp/src/main/kotlin/com/splitbill/app/MainActivity.kt`.

## Workflows Críticos

- **Build/Clean**: `./gradlew clean build`
- **Ejecutar Android**: `./gradlew :androidApp:installDebug`
- **Desktop (JVM)**: `./gradlew :kmp:run` (requiere definir `Main.kt` en `jvmMain`).
- **Sync**: Si cambias dependencias en `libs.versions.toml`, siempre ejecuta `./gradlew help` para forzar la resolución.

## Patrones y Convenciones

- **UI Primero**: La UI se define preferentemente en `commonMain` usando Material3 de Compose Multiplatform.
- **Resources**: Se gestionan mediante el plugin `compose.resources`.
  - Configuración en `kmp/build.gradle.kts` bajo `compose.resources`.
  - Acceso (típico): `Res.drawable.my_icon` (clase generada en el paquete configurado).
- **Tooling**: Las versiones de SDK y librerías clave están centralizadas en `gradle/libs.versions.toml`. **No** hardcodear versiones en archivos `.gradle.kts`.

## Integración

- **Inyección de Dependencias**: No hay una librería de DI pre-configurada (Koin/Hilt). Si se añade, debe seguir el patrón de `expected/actual` o inyección manual si es compartida.
- **Navegación**: Utiliza `navigation3-ui` de JetBrains/AndroidX (Alpha).
  - Ver `libs.versions.toml`: `compose-navigation3-ui`.

