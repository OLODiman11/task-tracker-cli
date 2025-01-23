package by.olodiman11.tasktrackercli.view;

import by.olodiman11.tasktrackercli.model.Task;

import java.lang.reflect.Method;
import java.util.List;

public interface StringView {
    String toTableView(List<Task> tasks);
    String toListView(List<Method> commands);
}
