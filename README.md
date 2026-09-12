# Chin

**Chin** is a small, keyboard-first task chatbot written in Java + JavaFX.

Try it: [User Guide](https://xy-241.github.io/ip/) • [Latest release](https://github.com/xy-241/ip/releases/latest)

## Quick start

Prerequisites: **JDK 21** (Corretto or Zulu).

1. Download `chin-all.jar` from the [Releases page](https://github.com/xy-241/ip/releases).
2. Run it:
   ```
   java -jar chin-all.jar
   ```
3. Type commands into the box at the bottom of the window and press Enter.

## Setting up in IntelliJ

1. Open the project as a Gradle project (accept default JDK 21 toolchain).
2. Locate `src/main/java/chin/Chin.java` (CLI) or `src/main/java/chin/Launcher.java` (JavaFX GUI).
3. Right-click → Run.
4. Or from the terminal: `./gradlew run` (GUI) — the `-ea` flag is enabled by default so runtime assertions fire.

## Repository layout

- `src/main/java/chin/` — application code (packages: `chin`, `chin.task`, `chin.util`)
- `src/test/java/chin/` — JUnit 5 tests
- `docs/` — User Guide + `Ui.png`
- `config/checkstyle/` — CheckStyle config (adapted from se-edu)
- `.github/workflows/gradle.yml` — CI (build + test on push/PR)

## Acknowledgements

- Starter template: [nus-cs2103-AY2627-S1/ip](https://github.com/NUS-CS2103-AY2627-S1/ip) (based on [se-edu/duke](https://github.com/se-edu/duke)).
- CheckStyle rules: adapted from [se-edu/addressbook-level3](https://github.com/se-edu/addressbook-level3).
- JavaFX head-start: [se-edu/javafx-tutorial](https://github.com/se-edu/javafx-tutorial); worked through in a separate fork at [xy-241/javafx-tutorial](https://github.com/xy-241/javafx-tutorial).
- Development assistance from Claude Code (Anthropic) for boilerplate + refactors; all design decisions are the author's.
