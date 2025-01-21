package by.olodiman11.tasktrackercli.command.impl;

import by.olodiman11.tasktrackercli.command.Command;

public class DeleteCommand implements Command {
    @Override
    public void execute(String... args) {
        if (args.length < 1) throw new IllegalArgumentException("Id is required");
        long id = Long.parseLong(args[0]);

        Command.changeTasksList(tasks ->
            tasks.stream()
                    .filter(task -> task.id() != id)
                    .toList());
    }
}
