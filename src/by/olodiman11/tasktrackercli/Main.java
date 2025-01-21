package by.olodiman11.tasktrackercli;

import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.mapper.JsonMapper;
import by.olodiman11.tasktrackercli.mapper.impl.JsonMapperImpl;
import by.olodiman11.tasktrackercli.model.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

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
        Task task = new Task(
                1,
                "Новая задача",
                TaskStatus.DONE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Path tasksPath = Path.of("tasks.json");
        Files.deleteIfExists(tasksPath);
        Files.createFile(tasksPath);
        Files.writeString(tasksPath, mapper.toJson(task));
        String json = Files.readString(tasksPath);
        Task task1 = mapper.fromJson(json, Task.class);
        System.out.println(task1);
    }
}