package by.olodiman11.tasktrackercli;

import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.mapper.JsonMapper;
import by.olodiman11.tasktrackercli.mapper.impl.JsonMapperImpl;
import by.olodiman11.tasktrackercli.model.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void run(String... args) throws Exception {
        JsonMapper mapper = new JsonMapperImpl();
        List<Task> tasks = createListOfTasks();
        Path tasksPath = Path.of("tasks.json");
        Files.deleteIfExists(tasksPath);
        Files.createFile(tasksPath);
        Files.writeString(tasksPath, mapper.toJsonList(tasks));
        String json = Files.readString(tasksPath);
        List<Task> tasks1 = mapper.fromJsonList(json, Task.class);
        System.out.println(tasks1);
    }

    private static List<Task> createListOfTasks() {
        return List.of(
                new Task(
                        1,
                        "Задача 1",
                        TaskStatus.TODO,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ),
                new Task(
                        2,
                        "Задача 2",
                        TaskStatus.IN_PROGRESS,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ),
                new Task(
                        3,
                        "Задача 3",
                        TaskStatus.DONE,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ));
    }
}