# MusicGPT Android App

A modern Android application built with Jetpack Compose that provides an AI-powered music creation interface with interactive music playback and smooth animations.

## Features

- **Modern UI**: Built with Jetpack Compose and Material 3 design
- **Dark Theme**: Elegant dark theme with orange, pink, and purple accent colors
- **Music Creation**: AI-powered music generation interface
- **Interactive Playback**: Click any music item to start playing with a now playing bar
- **Smooth Animations**: Beautiful animations throughout the interface
- **Responsive Design**: Optimized for various screen sizes
- **Navigation**: Bottom navigation with Create, Search, List, and Profile tabs

## Screenshots

The app features:
- Top app bar with animated MusicGPT logo and branding
- List of AI-generated content with status indicators and animations
- Interactive music items that start playback when clicked
- Now playing bar with playback controls (previous, play/pause, next)
- Floating action button with subtle breathing animation
- Bottom navigation with four main sections
- Processing indicators with animated progress bars
- Thumbnail animations for active AI tasks

## Music Playback Features

- **Click to Play**: Tap any music item to start playback
- **Now Playing Bar**: Appears above bottom navigation when music is playing
- **Playback Controls**: Previous, play/pause, and next track buttons
- **Visual Feedback**: Smooth animations and transitions
- **Status Indicators**: Shows processing state and progress

## Animations

- **Logo Animation**: Gentle breathing effect on the app logo
- **Card Animations**: Staggered entrance animations for list items
- **Thumbnail Effects**: Pulsing animation for processing items
- **Progress Bars**: Animated progress indicators for AI tasks
- **FAB Animation**: Subtle scale animation on the create button
- **Smooth Transitions**: Slide and fade animations for the now playing bar

## Technical Details

- **Minimum SDK**: 21 (Android 5.0)
- **Target SDK**: 35 (Android 15)
- **Architecture**: MVVM with Jetpack Compose
- **UI Framework**: Jetpack Compose with Material 3
- **Language**: Kotlin
- **Animations**: Jetpack Compose Animation APIs
- **State Management**: Compose state and LaunchedEffect

## Project Structure

```
app/src/main/java/com/music/gpt/
├── MainActivity.kt          # Main activity with UI implementation
└── ui/theme/
    ├── Color.kt             # Color definitions
    ├── Theme.kt             # App theme configuration
    └── Type.kt              # Typography definitions
```

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run the app on an emulator or device

## Dependencies

- Jetpack Compose BOM
- Material 3 components
- AndroidX core libraries
- Lifecycle components
- Compose Animation APIs

## Build

```bash
./gradlew assembleDebug
```

## Usage

1. **Browse Music**: View the list of AI-generated music content
2. **Play Music**: Tap any music item to start playback
3. **Control Playback**: Use the now playing bar controls
4. **Create New**: Use the floating action button to create new content
5. **Navigate**: Switch between different app sections using bottom navigation

## License

This project is for educational purposes.
