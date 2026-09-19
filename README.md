# News Reader Android App

A native Android news reader application built with Kotlin and Jetpack Compose.

The project follows **MVVM and Clean Architecture principles**, with a modular structure separating application, shared core infrastructure, and feature-specific functionality.

## Architecture

The project is organized into the following modules:

```text
app/
core/
├── core-api/
└── core-ui/
features/
└── newsfeed-feature/
    ├── data/
    ├── domain/
    └── presentation/
```

### App

Contains application-level components such as:

* `MainActivity`
* Application setup
* Navigation graph
* `NavHostProvider`

### Core

Shared infrastructure used across the application.

**core-api**

* Common API handling
* Error handling
* Base URL configuration
* Coroutine cancellation handling

**core-ui**

* Shared ViewModel/state infrastructure
* Common screen states
* Reusable screen and application UI components

### News Feed Feature

The feature follows a layered structure:

```text
presentation → domain ← data
```

The presentation layer depends only on the domain layer, keeping networking and data implementation details isolated from the UI.

## Features

* News article listing
* Infinite scroll pagination
* Pull-to-refresh
* Loading and error states
* Pagination error handling without losing already loaded articles
* Article navigation
* Protection against duplicate pagination requests
* Pagination reset after refresh

## Testing

`ArticlesViewModel` is covered with unit tests using **JUnit, MockK, and kotlinx-coroutines-test**.

Tests cover the main flows:

* Initial article loading
* Pagination
* Refresh
* Refresh failures
* Pagination reset after refresh
* Pagination starting from the correct page

## Tech Stack

* Kotlin
* Jetpack Compose
* MVVM
* Clean Architecture
* Kotlin Coroutines / StateFlow
* Hilt / KSP
* Retrofit / OkHttp
* JUnit
* MockK

## Git Workflow

Development was organized using feature branches and incremental commits, with completed features merged into the development branch before the final merge into `main`.
