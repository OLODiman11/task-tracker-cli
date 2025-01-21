package by.olodiman11.tasktrackercli.command.impl;

import by.olodiman11.tasktrackercli.command.Command;
import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.util.StringUtils;

import java.time.LocalDateTime;

public class UpdateCommand implements Command {
    @Override
    public void execute(String... args) {
        if (args.length < 2) throw new IllegalArgumentException("Id and description are required");
        long id = Long.parseLong(args[0]);
        String description = StringUtils.strip(args[1], "\"");

        Command.changeTasksList(tasks ->
                    tasks.stream()
                        .map(task -> task.id() == id ? updateTask(task, description) : task)
                        .toList());
    }

    private Task updateTask(Task task, String description) {
        return new Task(task.id(), description, task.status(), task.createdAt(), LocalDateTime.now());
    }
}
