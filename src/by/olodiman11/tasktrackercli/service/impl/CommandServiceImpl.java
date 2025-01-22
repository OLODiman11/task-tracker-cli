package by.olodiman11.tasktrackercli.service.impl;

import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.repository.TaskRepository;
import by.olodiman11.tasktrackercli.service.CommandService;

import java.time.LocalDateTime;
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
}
