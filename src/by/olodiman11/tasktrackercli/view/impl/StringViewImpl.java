package by.olodiman11.tasktrackercli.view.impl;

import by.olodiman11.tasktrackercli.model.Task;
import by.olodiman11.tasktrackercli.view.StringView;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringViewImpl implements StringView {

    @Override
    public String toString(List<Task> tasks) {
        List<List<String>> rows = new ArrayList<>();
        List<String> fieldNames = Arrays.stream(Task.class.getDeclaredFields())
                .map(Field::getName)
                .toList();
        rows.add(fieldNames);
        tasks.stream()
                .map(this::stringsOfFields)
                .forEach(rows::add);
        int[] columnWidths = IntStream.range(0, fieldNames.size())
                .map(col -> rows.stream()
                        .mapToInt(row -> row.get(col).length())
                        .max()
                        .getAsInt())
                .toArray();
        String template = Arrays.stream(columnWidths)
                .mapToObj(width -> "%" + width + "s")
                .collect(Collectors.joining("   "));
        return rows.stream()
                .map(row -> template.formatted(row.toArray()))
                .collect(Collectors.joining("\n"));
    }

    private List<String> stringsOfFields(Task task) {
        return List.of(
                String.valueOf(task.id()),
                task.description(),
                task.status().toString(),
                task.createdAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)),
                task.updatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
        );
    }
}
