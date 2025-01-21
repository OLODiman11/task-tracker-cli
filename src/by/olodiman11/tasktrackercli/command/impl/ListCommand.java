package by.olodiman11.tasktrackercli.command.impl;

import by.olodiman11.tasktrackercli.command.Command;
import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.util.StringUtils;
import by.olodiman11.tasktrackercli.view.StringView;
import by.olodiman11.tasktrackercli.view.impl.StringViewImpl;

import java.util.function.Predicate;

public class ListCommand implements Command {
    @Override
    public void execute(String... args) {
        Predicate<Task> predicate = args.length == 0
                ? _ -> true
                : getPredicate(args[0]);
        StringView stringView = new StringViewImpl();
        Command.changeTasksList(tasks -> {
            System.out.println(stringView.toString(tasks.stream()
                    .filter(predicate)
                    .toList()));
            return tasks;
        });
    }

    private Predicate<Task> getPredicate(String arg) {
        String statusString = StringUtils.strip(arg, "\"");
        TaskStatus status = Enum.valueOf(TaskStatus.class, statusString);
        return task -> task.status().equals(status);
    }
}
