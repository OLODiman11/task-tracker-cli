package by.olodiman11.tasktrackercli;

import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.mapper.JsonMapper;
import by.olodiman11.tasktrackercli.mapper.impl.JsonMapperImpl;
import by.olodiman11.tasktrackercli.model.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        createTasksFile();
        JsonMapper mapper = new JsonMapperImpl();
        Task task = new Task(
                1,
                "Новая задача",
                TaskStatus.IN_PROGRESS,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        try(Writer writer = new FileWriter("tasks.json")) {
             writer.write(mapper.toJson(task));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTasksFile() {
        try {
            boolean newFile = new File("tasks.json").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}