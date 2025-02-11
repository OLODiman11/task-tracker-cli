package by.olodiman11.tasktrackercli;

import by.olodiman11.tasktrackercli.controller.CommandController;
import by.olodiman11.tasktrackercli.controller.impl.CommandControllerImpl;
import by.olodiman11.tasktrackercli.dispatcher.CommandDispatcher;
import by.olodiman11.tasktrackercli.dispatcher.impl.CommandDispatcherImpl;
import by.olodiman11.tasktrackercli.mapper.JsonMapper;
import by.olodiman11.tasktrackercli.mapper.impl.JsonMapperImpl;
import by.olodiman11.tasktrackercli.repository.TaskRepository;
import by.olodiman11.tasktrackercli.repository.impl.TaskRepositoryImpl;
import by.olodiman11.tasktrackercli.service.CommandService;
import by.olodiman11.tasktrackercli.service.impl.CommandServiceImpl;
import by.olodiman11.tasktrackercli.view.StringView;
import by.olodiman11.tasktrackercli.view.impl.StringViewImpl;

import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Application {

    public static final Path TASKS_PATH = Path.of("tasks.json");
    public static final String CLI_PREFIX = "ttracker > ";

    private static final Pattern ARGS_PATTERN = Pattern.compile("\"([^\"]+)\"|(\\S+)");
    private static boolean running = true;

    public static void main(String[] args) {
        JsonMapper mapper = new JsonMapperImpl();
        TaskRepository repository = new TaskRepositoryImpl(TASKS_PATH, mapper);
        StringView view = new StringViewImpl();
        CommandService service = new CommandServiceImpl(repository);
        CommandController controller = new CommandControllerImpl(service, view);
        CommandDispatcher dispatcher = new CommandDispatcherImpl(controller);
        if (args.length == 0) startCliSession(dispatcher);
        else executeCommand(dispatcher, args);
    }


    private static void startCliSession(CommandDispatcher dispatcher) {
        String[] args;
        Scanner sc = new Scanner(System.in);
        while (running) {
            System.out.print(CLI_PREFIX);
            Matcher matcher = ARGS_PATTERN.matcher(sc.nextLine());
            args = Stream.iterate(matcher, Matcher::find, m -> m)
                    .map(m -> m.group(1) == null ? m.group(2) : m.group(1))
                    .toArray(String[]::new);
            executeCommand(dispatcher, args);
        }
    }

    private static void executeCommand(CommandDispatcher dispatcher, String[] args) {
        System.out.println(dispatcher.dispatch(args));
    }

    public static void stop() {
        running = false;
    }
}
