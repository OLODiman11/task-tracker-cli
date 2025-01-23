package by.olodiman11.tasktrackercli.controller;

import by.olodiman11.tasktrackercli.enums.TaskStatus;

public interface CommandController {
    String add(String description);
    String update(long id, String description);
    String delete(long id);
    String status(long id, TaskStatus status);
    String list();
    String list(TaskStatus status);
    String exit();
    String help();
    String help(String command);
}
