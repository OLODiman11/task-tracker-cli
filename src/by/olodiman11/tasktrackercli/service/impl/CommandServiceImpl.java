package by.olodiman11.tasktrackercli.service.impl;

import by.olodiman11.tasktrackercli.Application;
import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.exception.InvalidArgumentException;
import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.repository.TaskRepository;
import by.olodiman11.tasktrackercli.service.CommandService;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CommandServiceImpl implements CommandService {

    private final TaskRepository repository;

    public CommandServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public long add(String description) {
        List<Task> tasks = repository.findAll();
        long id = tasks.isEmpty() ? 1 : tasks.getLast().id() + 1;
        Task task = new Task(
                id,
                description,
                TaskStatus.TODO,
                LocalDateTime.now(),
                LocalDateTime.now());
        repository.add(task);
        repository.save();
        return id;
    }

    @Override
    public void update(long id, String description) {
        repository.update(id, task ->
                new Task(
                    task.id(),
                    description,
                    task.status(),
                    task.createdAt(),
                    LocalDateTime.now()));
        repository.save();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
        repository.save();
    }

    @Override
    public void status(long id, TaskStatus status) {
        repository.update(id, task ->
                new Task(
                    task.id(),
                    task.description(),
                    status,
                    task.createdAt(),
                    LocalDateTime.now()));
        repository.save();
    }

    @Override
    public List<Task> list() {
        return repository.findAll();
    }

    @Override
    public List<Task> list(TaskStatus status) {
        return repository.findAll(task -> task.status().equals(status));
    }

    @Override
    public void exit() {
        Application.stop();
    }

    @Override
    public List<Method> help() {
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> !method.isSynthetic())
                .toList();
    }

    @Override
    public List<Method> help(String command) {
        List<Method> methods = help();
        if(methods.stream().noneMatch(method -> method.getName().equals(command)))
            throw new InvalidArgumentException("Unknown command: " + command);
        return methods.stream()
                .filter(method -> method.getName().equals(command))
                .toList();
    }
}
