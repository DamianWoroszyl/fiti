# AGENTS.MD

# Architecture
This project is a modern Android application that follows the official architecture guidance from Google. It is a reactive, single-activity app that uses the following:

- **UI**: Built entirely with Jetpack Compose, including Material 3 components and adaptive layouts for different screen sizes.
- **State Management**: Unidirectional Data Flow (UDF) is implemented using Kotlin Coroutines and Flows. ViewModels act as state holders, exposing UI state as streams of data.
- **Dependency Injection**: Hilt is used for dependency injection throughout the app, simplifying the management of dependencies and improving testability.
- **Navigation**: Navigation is handled by Jetpack Navigation 3 for Compose, allowing for a declarative and type-safe way to navigate between screens. There's an app-specific wrapper over navigation to simplify and streamline usage in :core:ui modules

# Modules
- for all android modules create an :api and :impl module so that users of that module can import and use :api module rather than being forced to be an android module
- when starting a task always check the modules structure, make sure you place classes in appropriate modules

