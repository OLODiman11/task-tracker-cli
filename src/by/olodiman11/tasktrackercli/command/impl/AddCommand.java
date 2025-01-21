package by.olodiman11.tasktrackercli.command.impl;

import by.olodiman11.tasktrackercli.command.Command;
import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.model.Task;

import java.time.LocalDateTime;

public class AddCommand implements Command {
    @Override
    public void execute(String... args) {
        if (args.length < 1) throw new IllegalArgumentException("Description is required");
        String description = args[0];

        Command.changeTasksList(tasks -> {
                tasks.add(newTask(tasks.getLast().id() + 1, description));
                return tasks;
                });
    }

    private Task newTask(long id, String description) {
        return new Task(
                id,
                description,
                TaskStatus.TODO,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
