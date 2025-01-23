# Task Tracker CLI

[Roadmap Task Tracker CLI project](https://roadmap.sh/projects/task-tracker)

## Setup

Jar is compiled with `openjdk-22.0.1`.

1) download task-tracker-cli.jar file.
2) go to directory where the jar file is. `cd path/to/jar`

Or

1) clone repository
2) run Application.java in your IDE.

## Executing commands

Commands can be executed in two ways:
1) as command line argument:

```bash
java -jar task-tracker-cli.jar add "Task description"
New task added (id = 1)
java -jar task-tracker-cli.jar delete 1
Task deleted (id = 1)
```

2) in session mode:

```bash
java -jar task-tracker-cli.jar    # stating a session
ttracker > add "Task description"
New task added (id = 1)
ttracker > delete 1
Task deleted (id = 1)
exit                              # stopping a session
Exiting task tracker cli
```

## Commands

```bash
# List available commands
ttracker > help
1) exit 
2) update <long> <String>
3) add <String>
4) list 
5) list <TaskStatus>
6) status <long> <TaskStatus>
7) delete <long>
8) help 
9) help <String>

# List command options
ttracker > help list
1) list 
2) list <TaskStatus>

# Add task with description
ttracker > add "New task"
Task added (id = 1)

# Update task description
ttracker > update 1 "New description"
Task updated (id = 1)

# Delete task by index
ttracker > delete 1
Task deleted (id = 1)

# List all tasks
ttracker > list 
id   description        status           createdAt           updatedAt
 2        update   IN_PROGRESS   21.01.2025, 23:50   22.01.2025, 01:14
 3        Task 3          DONE   21.01.2025, 23:50   21.01.2025, 23:50
 4           qwe          TODO   22.01.2025, 01:13   23.01.2025, 11:01
 5     qweqweewq          TODO   22.01.2025, 05:53   22.01.2025, 05:53

# List all tasks with status
ttracker > list TODO
id   description   status           createdAt           updatedAt
 4           qwe     TODO   22.01.2025, 01:13   23.01.2025, 11:01
 5     qweqweewq     TODO   22.01.2025, 05:53   22.01.2025, 05:53

# Change status of a task
ttracker > status 4 IN_PROGRESS
Task status changed (id = 4, status = IN_PROGRESS)

# Stop session
exit
Exiting task tracker cli
```