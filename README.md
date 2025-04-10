![DevDeck Logo](https://github.com/KoviElango/DevDeck/blob/master/app/src/main/res/drawable/devdeck_logo.png?raw=true)

# DevDeck

DevDeck is a simple Android application built using Kotlin and Jetpack Compose. It allows users to search for GitHub profiles, view user details, and explore their followers and following lists. The application leverages GitHub's public API and is designed to be beginner-friendly, clean, and minimal.

---

## Features

- Search GitHub users by username
- View detailed profiles including avatar, name, bio, and ID
- View followers and following lists
- Smooth infinite scroll for followers/following
- Minimal and modern UI using Material Design 3

---

## Tech Stack

- **Language:** Kotlin  
- **UI Framework:** Jetpack Compose  
- **Network:** Retrofit + Gson  
- **Architecture:** MVVM  
- **State Management:** StateFlow + ViewModel  

---

Notes
- The app uses the GitHub public API, which has rate limits for unauthenticated users.
- No login is required.
- Designed as an MVP (Minimum Viable Product) and open to contributions or expansion.
