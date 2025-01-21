package by.olodiman11.tasktrackercli.mapper.impl;

import by.olodiman11.tasktrackercli.mapper.JsonMapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JsonMapperImpl implements JsonMapper {

    @Override
    public <T> String toJson(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return "{" + Arrays.stream(fields)
                .map(field -> formatKeyValuePair(field.getName(), getFieldValue(field, object)))
                .collect(Collectors.joining(",")) + "}";
    }

    private Object getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get field: " + field.getName(), e);
        }
    }

    private String formatKeyValuePair(String key, Object value) {
        return String.format("\"%s\":\"%s\"", key, value.toString());
    }
}
