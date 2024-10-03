### Ready Play

# Introduction

This Android application fetches and displays a store populated with list of movies using the public [TMDB API](https://api.themoviedb.org/3/). An api key would be needed for this api. Add the api key to local.properties using (API_KEY="VALUE")

The project is built using the MVVM architectural pattern and leverages several modern libraries and tools to ensure a robust and scalable codebase.

## Features

- Basic Login 
- Fetch and display Store Items(Movies) and their details
- Add to Cart, QR code scanning using Zxing Library
- MVVM architecture with domain, data, and repository layers
- Modern UI with Jetpack Compose
- Dependency Injection with Koin
- Network requests with Retrofit and Moshi
- Image loading with Coil
- Unit and Instrumented tests

## Architecture

The project follows the MVVM (Model-View-ViewModel) architectural pattern, divided into the following layers:

1. **Domain Model**: Represents the business logic and data entities.
2. **Data Model**: Represents the data structures used by the network and database layers.
3. **Repository Layer**: Maps data models to domain models and handles data operations.
4. **Use Cases**: Encapsulate individual pieces of business logic.
5. **ViewModel**: Prepares data for the UI and handles UI-related logic.
6. **UI**: Written in Jetpack Compose, the UI layer observes the ViewModel and reacts to data changes.

### Library Usage

- **Compose**: Used to build a modern, responsive UI with less boilerplate code.
- **Retrofit**: Facilitates HTTP requests to fetch data from the HP API.
- **Moshi**: Converts JSON responses into Kotlin objects.
- **Koin**: Manages dependencies across the application, providing a simple and lightweight DI framework.
- **MockK**: Provides mocking capabilities for unit tests.
- **Coil**: Efficiently loads and caches images, integrated seamlessly with Compose.
- **Zxing**: Scan Movie QR code to fetch details.


## Testing

### Unit Tests

Unit tests are written to verify the functionality of individual components in isolation. MockK is used to mock dependencies and verify interactions.

### Instrumented Tests

Instrumented tests run on an Android device or emulator to verify the app's behavior in a real-world environment. There are two sub folders under android tests:

1. **Manual Dependency Injection**: Tests where dependencies are manually provided.
2. **Koin, MockK and Stubs**: Tests that use a koinTestRule and MockK to mock dependencies and provide stubs.


## Instructions on how to get started.

- Install the Android SDK & Android Studio [Android Studio](https://developer.android.com/studio)
  from the Internet.
- Complete the installation process.
- Git Clone the repo [Repository](https://github.com/Okekeprince1/Ready-Play) using ```git clone https://github.com/Okekeprince1/Ready-Play.git```
  to your local machine using either the terminal or directly cloning using the Android Studio.
