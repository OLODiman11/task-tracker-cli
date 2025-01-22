package by.olodiman11.tasktrackercli.repository.impl;

import by.olodiman11.tasktrackercli.mapper.JsonMapper;
import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.repository.TaskRepository;
import by.olodiman11.tasktrackercli.util.FileUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class TaskRepositoryImpl implements TaskRepository {

    private final Path path;
    private final JsonMapper mapper;
    private final List<Task> tasks;

    public TaskRepositoryImpl(Path path, JsonMapper mapper) {
        FileUtils.createFileIfAbsent(path);
        this.path = path;
        this.mapper = mapper;
        this.tasks = mapper.fromJsonList(FileUtils.readString(path), Task.class);
    }

    @Override
    public List<Task> findAll() {
        return findAll(_ -> true);
    }

    @Override
    public List<Task> findAll(Predicate<Task> predicate) {
        return tasks.stream().filter(predicate).toList();
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public void update(long id, UnaryOperator<Task> update) {
        tasks.replaceAll(task -> task.id() == id ? update.apply(task) : task);
    }

    @Override
    public void delete(long id) {
        tasks.removeIf(task -> task.id() == id);
    }


    @Override
    public void save() {
        FileUtils.writeString(path, mapper.toJsonList(tasks));
    }
}
