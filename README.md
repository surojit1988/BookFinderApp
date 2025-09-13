# BookFinder App 📚

An Android app built with **Jetpack Compose**, **Hilt**, **Room**, and **Retrofit** for searching books using the **OpenLibrary API**.

---

## Table of Contents

1. [Features](#features)  
2. [Screenshots](#screenshots)  
3. [Tech Stack](#tech-stack)  
4. [Setup Instructions](#setup-instructions)  
5. [Project Structure](#project-structure)  
6. [API Usage](#api-usage)  
7. [Running the App](#running-the-app)  
8. [Testing](#testing)  
9. [Contributing](#contributing)  
10. [License](#license)  

---

## Features

- Search books by title with OpenLibrary API.  
- Infinite scrolling and paginated results.  
- View book details with cover, authors, and description.  
- Save favorite books locally using Room database.  
- Swipe-to-refresh support.  
- Shimmer animations for loading states.  
- MVVM architecture with Hilt DI.  

---

## Screenshots

*(Add screenshots here)*

---

## Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose, Material3  
- **Networking:** Retrofit, Moshi, OkHttp  
- **Dependency Injection:** Hilt  
- **Database:** Room  
- **Image Loading:** Coil  
- **Testing:** JUnit, Mockito, Coroutines Test
- **Extras:** Shimmer animations 

  Project Structure
  .
├── data
│   ├── local        # Room database, DAO, Entities
│   ├── model        # DTOs for API responses
│   └── remote       # Retrofit API interfaces
├── domain
│   ├── model        # App-level models (Book, WorkDetails)
│   └── repository   # Repository interface
├── ui
│   ├── screens      # Compose Screens (Search, Details)
│   └── components   # Reusable UI components (BookItem, Shimmer)
├── usecases         # Business logic (SearchBooks, GetWorkDetails, etc.)
├── di               # Hilt modules
└── navigation       # NavGraph
API Usage

Search books:
https://openlibrary.org/search.json?title={query}&limit=20&page={page}

Book details:
https://openlibrary.org/works/{work_id}.json

Running the App:
Launch the app.
Enter a book title in the search bar.
Scroll down to load more results.
Tap a book to view details.
Save or remove favorites.

Testing:
Unit tests available for use cases and ViewModels.
Run tests using:
./gradlew test

Test framework: JUnit 4, Mockito-Kotlin, Coroutines Test.


---
