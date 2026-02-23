# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Fiti is an Android calorie-tracking app with voice assistant integration, built with Jetpack Compose and clean architecture. It targets API 24+, compiles against API 36, uses Kotlin 2.1.20, and Java 17.

## Common Commands

```bash
# Build
./gradlew assembleDebug
./gradlew assembleRelease

# Tests
./gradlew test                    # Unit tests (all modules)
./gradlew :<module>:test          # Single module unit tests
./gradlew connectedAndroidTest    # On-device instrumented tests

# Code quality
./gradlew detekt                  # Static analysis (config: config/detekt/config.yml)

# Install
./gradlew installDebug
```

# Architecture
This project is a modern Android application that follows the official architecture guidance from Google. It is a reactive, single-activity app that uses the following:

- **UI**: Built entirely with Jetpack Compose, including Material 3 components and adaptive layouts for different screen sizes.
- **State Management**: Unidirectional Data Flow (UDF) is implemented using Kotlin Coroutines and Flows. ViewModels act as state holders, exposing UI state as streams of data.
- **Dependency Injection**: Hilt is used for dependency injection throughout the app, simplifying the management of dependencies and improving testability.
- **Navigation**: Navigation is handled by Jetpack Navigation 3 for Compose, allowing for a declarative and type-safe way to navigate between screens. There's an app-specific wrapper over navigation to simplify and streamline usage in :core:ui modules

# Modules
- for all android modules create an :api and :impl module so that users of that module can import and use :api module rather than being forced to be an android module
- when starting a task always check the modules structure, make sure you place classes in appropriate modules

## Convention Plugins (build-logic/)

All modules use convention plugins instead of repeating build config:

| Plugin | Usage |
|--------|-------|
| `fiti.android.application` | App module |
| `fiti.android.library` | Android lib modules |
| `fiti.jvm.library` | Pure Kotlin modules |
| `fiti.android.compose` | Adds Compose dependencies |
| `fiti.android.hilt` | Adds Hilt DI setup + KSP |
| `fiti.android.room` | Adds Room + KSP schema export |
| `fiti.kotlin.detekt` | Adds detekt static analysis |

## Key Architectural Patterns

**Navigation:** Jetpack Navigation 3 with type-safe, `@Serializable` navigation keys. Screens register via `EntryProvider`/entry builder pattern in `core:ui:impl`. The `Navigator` interface (in `core:ui:api`) is injected into ViewModels.

**State management:** Hilt ViewModels with `StateFlow`/`SharedFlow`, unidirectional data flow. Use `@AssistedFactory` + `@AssistedInject` when ViewModels need runtime parameters (e.g., navigation keys).

**Dependency injection:** Hilt throughout. Pure Kotlin modules use `javax.inject` interfaces; Android modules provide `@Module`/`@InstallIn` bindings.

**New feature module checklist:**
1. Create API module (pure Kotlin, `fiti.jvm.library`) with interfaces/models
2. Create impl module (Android, `fiti.android.library`) with implementations
3. Add both to `settings.gradle.kts`
4. Wire impl module into `app/build.gradle.kts`
5. Add Hilt `@Module` in impl module to bind API → impl

## Dependencies & Versions

Managed via `gradle/libs.versions.toml` (version catalog). Key versions:
- Compose BOM: `2025.06.01`
- Hilt: `2.56.2`
- Room: `2.7.2`
- KSP: `2.1.20-2.0.1`
- Detekt: `1.23.8`
- Coroutines: `1.10.2`
- Kotlinx Serialization: `1.9.0`
