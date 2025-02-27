# Dependencies Overview

This document outlines the dependencies used in **MagicApp**, specifying their purpose and integration details.

## Navigation

- **Navigation Component** (`androidx.navigation:navigation-fragment-ktx` & `androidx.navigation:navigation-ui-ktx`)
    - Simplifies navigation and argument passing between fragments.
- **Safe Args Plugin** (`androidx.navigation.safeargs.kotlin`)
    - Ensures type-safe argument passing.

## Dependency Injection

- **Koin** (`io.insert-koin:koin-android`)
    - Manages dependency injection efficiently.
- **KSP for Koin** (`io.insert-koin:koin-ksp-compiler`)
    - Optimizes Koin annotation processing with KSP.

## Local Database

- **Room Database** (`androidx.room:room-runtime`)
    - Provides an abstraction layer over SQLite.
- **Room KSP Compiler** (`androidx.room:room-compiler`)
    - Optimizes Room annotation processing with KSP.
- **Room Coroutines Support** (`androidx.room:room-ktx`)
    - Enables coroutine-based database operations.

## Networking

- **Retrofit** (`com.squareup.retrofit2:retrofit`)
    - Simplifies API requests and responses.
- **Gson Converter** (`com.squareup.retrofit2:converter-gson`)
    - Converts JSON responses into Kotlin objects.
- **OkHttp** (`com.squareup.okhttp3:logging-interceptor`)
    - Logs network requests and responses for debugging.

## Image Loading

- **Coil** (`io.coil-kt:coil-compose`)
    - Efficiently loads and caches images.

## Build Tools

- **Kotlin Symbol Processing (KSP)** (`com.google.devtools.ksp`)
    - Accelerates annotation processing.
- **Android Gradle Plugin (AGP)** (`com.android.application`)
    - Manages project building and compiling.

For more details, refer to the `libs.versions.toml` configuration file.

