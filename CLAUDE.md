# Othello Game Project Guide

## Build & Test Commands
- Build: `./gradlew build`
- Run: `./gradlew run`
- Test all: `./gradlew test`
- Test single class: `./gradlew test --tests TestClassName`
- Test single method: `./gradlew test --tests TestClassName.testMethodName`
- JavaFX dist: `./gradlew jlinkZip`

## Code Style Guidelines
- **Package Structure**: `fr.univ_amu.m1info.board_game_library.*`
- **Naming Conventions**:
  - Classes: PascalCase (e.g., `BoardGameView`)
  - Methods/Variables: camelCase (e.g., `getPosition()`)
  - Constants: UPPER_SNAKE_CASE
- **Design Patterns**: MVC architecture, Factory, Iterator, Command
- **Fields**: Private with public getters/setters
- **Documentation**: JavaDoc for public methods and classes
- **Imports**: Java standard first, then frameworks, then project classes
- **Error Handling**: Prefer explicit exceptions with descriptive messages
- **Testing**: JUnit 5 with descriptive test method names