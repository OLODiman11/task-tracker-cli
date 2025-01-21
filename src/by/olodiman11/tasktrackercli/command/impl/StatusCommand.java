package by.olodiman11.tasktrackercli.command.impl;

import by.olodiman11.tasktrackercli.command.Command;
import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.util.StringUtils;

import java.time.LocalDateTime;

public class StatusCommand implements Command {

    @Override
    public void execute(String... args) {
        if (args.length < 2) throw new IllegalArgumentException("Id and status are required");
        long id = Long.parseLong(args[0]);
        String statusString = StringUtils.strip(args[1], "\"");
        TaskStatus status = Enum.valueOf(TaskStatus.class, statusString);

        Command.changeTasksList(tasks ->
                tasks.stream()
                        .map(task -> task.id() == id ? updateTask(task, status) : task)
                        .toList());
    }

    private Task updateTask(Task task, TaskStatus status) {
        return new Task(task.id(), task.description(), status, task.createdAt(), LocalDateTime.now());
    }
}
