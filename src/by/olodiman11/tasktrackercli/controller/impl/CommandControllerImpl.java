package by.olodiman11.tasktrackercli.controller.impl;

import by.olodiman11.tasktrackercli.controller.CommandController;
import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.service.CommandService;
import by.olodiman11.tasktrackercli.view.StringView;

import java.util.List;

public class CommandControllerImpl implements CommandController {

    private final CommandService service;
    private final StringView view;

    public CommandControllerImpl(CommandService service, StringView view) {
        this.service = service;
        this.view = view;
    }

    @Override
    public String add(String description) {
        long id = service.add(description);
        return "New task added (id = " + id + ")";
    }

    @Override
    public String update(long id, String description) {
        service.update(id, description);
        return "Task updated (id = " + id + ")";
    }

    @Override
    public String delete(long id) {
        service.delete(id);
        return "Task deleted (id = " + id + ")";
    }

    @Override
    public String status(long id, TaskStatus status) {
        service.status(id, status);
        return "Task status changed (id = " + id + ", status = " + status + ")";
    }

    @Override
    public String list() {
        List<Task> tasks = service.list();
        return view.toString(tasks);
    }

    @Override
    public String list(TaskStatus status) {
        List<Task> tasks = service.list(status);
        return view.toString(tasks);
    }
}
