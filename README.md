# ControlX

ControlX is an Android app that provides quick controls for common phone features. This repository contains the Android Studio project for ControlX — a simple, user-friendly app to toggle and manage device features.

## Features
- Toggle Wi‑Fi and Bluetooth (where supported by the OS)
- Turn flashlight on/off
- Adjust volume and ringer mode
- Quick access to commonly used settings
- Simple, lightweight UI built with Java (Android)

> Note: Specific available features depend on the Android API level and device permissions.

## Screenshots
- screenshots/screen1.png — Application services / controls list
- screenshots/screen2.png — Explore services / logout screen
- screenshots/screen3.png — Welcome / onboarding screen
- screenshots/screen4.png — Login / register screen


![Application Services](screenshots/screen1.png)
*Application services — calculator, music player, camera, browser, quiz, etc.*

![Explore Services](screenshots/screen2.png)
*Explore Services / Logout screen.*

![Welcome](screenshots/screen3.png)
*Welcome to ControlX onboarding screen.*

![Auth](screenshots/screen4.png)
*Login / Register screen.*


## Tech stack
- Android (Java)
- Project contains some HTML/CSS/JavaScript resources (if used in WebViews or static pages)
- Gradle for build and dependency management

## Getting started

### Prerequisites
- Android Studio (latest stable)
- Android SDK and an emulator or a physical Android device
- JDK 11+ (or the version required by the project)

### Run locally
1. Clone the repository:
   git clone https://github.com/shivamkumar617230/Android-Studio-Project.git
2. Open Android Studio and choose "Open an existing Android Studio project", then select the cloned folder.
3. Let Gradle sync and build the project.
4. Connect an Android device (enable USB debugging) or start an emulator.
5. Run the app (Run -> Run 'app').

## Permissions
ControlX needs runtime permissions for some features. Typical permissions (declare in AndroidManifest.xml and request at runtime) may include:
- android.permission.CAMERA (for flashlight & camera use)
- android.permission.MODIFY_AUDIO_SETTINGS (for volume)
- android.permission.ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION (only if a feature requires location)
- android.permission.BLUETOOTH / BLUETOOTH_ADMIN (legacy; behaviour varies by Android version)
- android.permission.ACCESS_WIFI_STATE / android.permission.CHANGE_WIFI_STATE (note: changing Wi‑Fi programmatically is restricted on newer Android versions)

Always follow platform guidance: on recent Android releases some toggles require sending the user to system settings or using new APIs instead of direct programmatic switches.

## Troubleshooting
- If a toggle doesn't work, check Android API level restrictions and runtime permission grants.
- Review Logcat for errors and exceptions.
- Ensure the project uses a compatible targetSdkVersion and compileSdkVersion in `build.gradle`.

## Contributing
Contributions are welcome. To contribute:
1. Fork the repo.
2. Create a branch: git checkout -b feature/your-feature
3. Commit your changes: git commit -m "Add feature"
4. Push to the branch and open a pull request.

Please include clear descriptions and testing steps.

## License
This project is available under the MIT License. See LICENSE for details.

## Contact
Maintained by shivamkumar617230 — open an issue or contact me via GitHub for questions or feature requests.
