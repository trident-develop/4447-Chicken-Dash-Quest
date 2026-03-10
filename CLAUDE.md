# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Chicken Dash Quest is an Android app built with Kotlin and Jetpack Compose. Package: `com.gamegos.adventure.bay.paradise.f`.

## Build Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew test                   # Run unit tests
./gradlew connectedAndroidTest   # Run instrumented tests on device/emulator
./gradlew app:testDebugUnitTest  # Run single module unit tests
```

## Architecture

- **Single module** (`app`) project using Gradle Kotlin DSL with version catalog (`libs.versions.toml`)
- **LoadingActivity** is the launcher entry point, **MainActivity** is the main screen
- UI built with Jetpack Compose and Material 3, edge-to-edge enabled
- Min SDK 28, target SDK 36, Java 11 compatibility
