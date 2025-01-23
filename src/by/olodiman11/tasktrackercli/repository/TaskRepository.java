package by.olodiman11.tasktrackercli.repository;

import by.olodiman11.tasktrackercli.model.Task;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface TaskRepository {
    List<Task> findAll();
    List<Task> findAll(Predicate<Task> predicate);
    void add(Task task);
    void update(long id, UnaryOperator<Task> update);
    void delete(long id);
    void save();
}
