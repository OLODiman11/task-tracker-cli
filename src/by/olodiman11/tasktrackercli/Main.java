package by.olodiman11.tasktrackercli;

import by.olodiman11.tasktrackercli.command.Command;
import by.olodiman11.tasktrackercli.enums.TaskStatus;
import by.olodiman11.tasktrackercli.model.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static final Path TASKS_PATH = Path.of("tasks.json");

    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void run(String... args) throws Exception {
        if (args.length == 0) throw new IllegalArgumentException("No command provided");

//        Files.deleteIfExists(TASKS_PATH);
        if(!Files.exists(TASKS_PATH))
            Files.createFile(TASKS_PATH);

        String[] arguments = Arrays.copyOfRange(args, 1, args.length);
        Command.execute(args[0], arguments);

//        JsonMapper mapper = new JsonMapperImpl();
//        List<Task> tasks = createListOfTasks();
//        Files.writeString(TASKS_PATH, mapper.toJsonList(tasks));
//        String json = Files.readString(TASKS_PATH);
//        List<Task> tasks1 = mapper.fromJsonList(json, Task.class);
//        System.out.println(tasks1);
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