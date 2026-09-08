# Botavius

Botavius is a command-line task manager written in Java. It supports to-do,
deadline, and event tasks, and saves the task list to `save.txt` when the
application exits.

## Prerequisites

- JDK 25
- IntelliJ IDEA (optional)

## Running the application

From the project directory, run:

```text
./gradlew run
```

On Windows, use:

```text
gradlew.bat run
```

The application starts with a greeting and waits for one command per line.
Enter `bye` to save the current task list and exit.

To create an executable JAR, run:

```text
./gradlew shadowJar
```

The resulting file is `build/libs/botavius.jar`.

## Commands

| Command | Example | Purpose |
| --- | --- | --- |
| `todo` | `todo buy milk` | Adds a to-do task |
| `deadline` | `deadline return book /by Sunday` | Adds a task with a deadline |
| `event` | `event project meeting /from Mon 2pm /to 4pm` | Adds an event |
| `list` | `list` | Displays all tasks |
| `find` | `find book` | Displays tasks whose descriptions contain the search text |
| `mark` | `mark 1` | Marks a task as done |
| `unmark` | `unmark 1` | Marks a task as not done |
| `delete` | `delete 1` | Removes a task |
| `bye` | `bye` | Saves the task list and exits |

Commands should begin with one of the command names shown above; an unsupported
command produces an error message.

## Project structure

- `src/main/java/botavius/Botavius.java` — application entry point
- `src/main/java/botavius/ui` — console input and output
- `src/main/java/botavius/parser` — command parsing
- `src/main/java/botavius/tasklist` — task types and task-list operations
- `src/main/java/botavius/storage` — loading and saving tasks
- `src/test/java` — JUnit tests

## Testing

Run the automated tests with:

```text
./gradlew test
```

The project expects Java source files to remain under
`src/main/java`, which is the standard Gradle source location.
