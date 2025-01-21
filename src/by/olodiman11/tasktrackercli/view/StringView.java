package by.olodiman11.tasktrackercli.view;

import by.olodiman11.tasktrackercli.model.Task;

import java.util.List;

public interface StringView {
    String toString(List<Task> tasks);
}
