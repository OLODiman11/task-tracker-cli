package by.olodiman11.tasktrackercli.dispatcher.impl;

import by.olodiman11.tasktrackercli.controller.CommandController;
import by.olodiman11.tasktrackercli.dispatcher.CommandDispatcher;
import by.olodiman11.tasktrackercli.exception.InvalidArgumentException;
import by.olodiman11.tasktrackercli.exception.UnknownCommandException;
import by.olodiman11.tasktrackercli.util.ReflectionUtils;
import by.olodiman11.tasktrackercli.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CommandDispatcherImpl implements CommandDispatcher {

    private final CommandController controller;

    public CommandDispatcherImpl(CommandController controller) {
        this.controller = controller;
    }

    @Override
    public String dispatch(String... args) {
        try {
            String command = args[0];
            String[] arguments = Arrays.stream(args)
                    .skip(1)
                    .map(StringUtils::unquote)
                    .toArray(String[]::new);
            Method method = getMethod(command, arguments);
            Object[] parsedArgs = parseArguments(arguments, method.getParameterTypes());
            return (String) ReflectionUtils.invokeMethod(method, controller, parsedArgs);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private Method getMethod(String name, String... args) {
        try {
            return Arrays.stream(controller.getClass().getDeclaredMethods())
                    .filter(method -> method.getName().equals(name) && method.getParameterCount() == args.length)
                    .findFirst()
                    .get();
        } catch (Exception e) {
            throw new UnknownCommandException("Unknown command: " + name + " with " + args.length + " arguments", e);
        }
    }

    private Object[] parseArguments(String[] arguments, Class<?>[] parameterTypes) {
        return IntStream.range(0, arguments.length)
                .mapToObj(i -> castArgument(arguments[i], parameterTypes[i]))
                .toArray(Object[]::new);
    }

    private Object castArgument(String arg, Class<?> type) {
        try {
            if(type.equals(long.class))
                return Long.parseLong(arg);
            if(type.isEnum())
                return Enum.valueOf((Class<? extends Enum>) type, arg);
            return type.cast(arg);
        } catch (Exception e) {
            throw new InvalidArgumentException("Invalid argument: " + arg + ". Expected type: " + type, e);
        }
    }
}
