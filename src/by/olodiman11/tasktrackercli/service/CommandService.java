package by.olodiman11.tasktrackercli.service;

import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.model.Task;

import java.util.List;

public interface CommandService {
    long add(String description);
    void update(long id, String description);
    void delete(long id);
    void status(long id, TaskStatus status);
    List<Task> list();
    List<Task> list(TaskStatus status);
    void exit();
}
