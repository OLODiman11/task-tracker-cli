package by.olodiman11.tasktrackercli.command;

import by.olodiman11.tasktrackercli.Main;
import by.olodiman11.tasktrackercli.mapper.JsonMapper;
import by.olodiman11.tasktrackercli.mapper.impl.JsonMapperImpl;
import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.util.List;
import java.util.function.*;

public interface Command {
    void execute(String... args);
    static void execute(String commandString, String... args) {
        Command command = parseCommand(commandString);
        command.execute(args);
    }

    private static Command parseCommand(String arg) {
        String className = Command.class.getPackageName() + ".impl." + arg.substring(0, 1).toUpperCase() + arg.substring(1) + Command.class.getSimpleName();
        try {
            Class<? extends Command> clazz = (Class<? extends Command>) Class.forName(className);
            Constructor<? extends Command> constructor = ReflectionUtils.getConstructor(clazz);
            return ReflectionUtils.newInstance(constructor);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown command: " + arg);
        }
    }

    static void changeTasksList(UnaryOperator<List<Task>> operator) {
        try {
            String json = Files.readString(Main.TASKS_PATH);
            JsonMapper mapper = new JsonMapperImpl();
            List<Task> tasks = mapper.fromJsonList(json, Task.class);
            tasks = operator.apply(tasks);
            String json2 = mapper.toJsonList(tasks);
            Files.writeString(Main.TASKS_PATH, json2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
