package by.olodiman11.tasktrackercli.view.impl;

import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.view.StringView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringViewImpl implements StringView {

    private final List<String> header = getHeader();

    @Override
    public String toTableView(List<Task> tasks) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(header);
        rows.addAll(getData(tasks));

        List<Integer> widths = getColumnWidths(rows);

        String template = getRowTemplate(widths);

        return rows.stream()
                .map(row -> template.formatted(row.toArray()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toListView(List<Method> commands) {
        return IntStream.range(0, commands.size())
                .mapToObj(i -> {
                    Method method = commands.get(i);
                    String prefix = i + 1 + ") " + method.getName() + " ";
                    return prefix + Arrays.stream(method.getParameterTypes())
                            .map(type -> "<" + type.getSimpleName() + ">")
                            .collect(Collectors.joining(" "));
                }).collect(Collectors.joining("\n"));
    }

    private List<Integer> getColumnWidths(List<List<String>> rows) {
        return IntStream.range(0, rows.getFirst().size())
                .mapToObj(col -> rows.stream()
                        .mapToInt(row -> row.get(col).length())
                        .max()
                        .getAsInt())
                .toList();
    }

    private String getRowTemplate(List<Integer> widths) {
        return widths.stream()
                .map(width -> "%" + width + "s")
                .collect(Collectors.joining("   "));

    }

    private List<List<String>> getData(List<Task> tasks) {
        return tasks.stream()
                .map(this::getRow)
                .toList();
    }

    private List<String> getRow(Task task) {
        return List.of(
                String.valueOf(task.id()),
                task.description(),
                task.status().toString(),
                task.createdAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)),
                task.updatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
        );
    }

    private List<String> getHeader() {
        return Arrays.stream(Task.class.getDeclaredFields())
                .map(Field::getName)
                .toList();
    }
}
