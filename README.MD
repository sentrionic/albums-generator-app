# 1001 AlbumsGenerator App
Android companion app to https://1001albumsgenerator.com/.
This is a Kotlin Multiplatform project targeting Android, iOS, Desktop (JVM).

## Demo Video

[Demo.mp4 (external site)](https://sentrionic-s3-bucket.s3.eu-central-1.amazonaws.com/demo.mp4)

## Features
- Offline-First (you still need to be online to rate and get your next album)
- Dark Mode
- Most features from the website:
  - Current Album
  - History
  - Summary
  - Global Statistics
  - Rate your album using the built-in webview
- Android App & Desktop
  - So far Desktop basically runs the Android design. The design isn't customized to fit desktop yet. You should view the desktop app as a way to run the Android app.

## Stack

The data displayed by the app is from [1001AlbumsGenerator API](https://www.reddit.com/r/1001AlbumsGenerator/comments/p6xw6y/json_api/).

The app uses the following multiplatform dependencies in its implementation:

- [Compose Multiplatform](https://jb.gg/compose) for UI
- [Compose Navigation 3](https://kotlinlang.org/docs/multiplatform/compose-navigation-3.html)
- [Ktor](https://ktor.io/) for networking
- [Coil](https://github.com/coil-kt/coil) for image loading
- [Metro](https://zacsweers.github.io/metro/latest/) for dependency injection
- [Room](https://developer.android.com/training/data-storage/room) for local storage
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) for key-value storage
- [Kotlinter](https://github.com/jeremymailen/kotlinter-gradle) for linting and formatting

## Architecture
Currently all shared code in inside the `sharedUI/commonMain` directory.
- `datasources`: Network & Cache related code
- `di`: Dependency Injection configuration
- `domain`: Domain models
- `presentation`: UI related code

## Known Issues
- https://github.com/saral-apps/ComposeMultiplatformWebview/issues/4
- Rating an album does not update the value in the history
  - This is because we fetch the info on app start, including the unrated album. In theory, we could refetch the entire info after rating again, but that seems wasteful.

## Credits
- https://1001albumsgenerator.com/ Fantastic project, absolutely loving it
- [Robert Munro](https://medium.com/@rrmunro) Github Actions

### Icons
- Logo https://www.shutterstock.com/image-vector/dove-pigeon-on-vinyl-record-music-2039271557
- [Amazon Music](href="https://icons8.com/icon/Dw8hR8kffAeB/amazon-music) from [Icon8](https://icons8.com)
- Login Screen https://wallpapersafari.com/w/MwKbEO
- Tabler Icons: Wikipedia, Tidal, Spotify, Deter
- Simple Icons: Apple Music, YouTube Music
- Lucide Icons: Star
- Qobuz https://www.svgrepo.com/svg/504778/qobuz

## KMP Setup

### Build and Run Android Application
To run the application on android device/emulator:
- open project in Android Studio and run imported android run configuration

To build the application bundle:
- run `./gradlew :androidApp:assembleDebug`
- find `.apk` file in `androidApp/build/outputs/apk/debug/androidApp-debug.apk`

### Desktop
Run the desktop application: `./gradlew :desktopApp:run`  
Run the desktop **hot reload** application: `./gradlew :desktopApp:hotRun --auto`

### iOS
To run the application on iPhone device/simulator:
- Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
- Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
