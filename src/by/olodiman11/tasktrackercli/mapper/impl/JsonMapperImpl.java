package by.olodiman11.tasktrackercli.mapper.impl;

import by.olodiman11.tasktrackercli.mapper.JsonMapper;
import by.olodiman11.tasktrackercli.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonMapperImpl implements JsonMapper {

    @Override
    public <T> String toJson(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return "{" + Arrays.stream(fields)
                .map(field -> formatKeyValuePair(field.getName(), ReflectionUtils.getFieldValue(field, object)))
                .collect(Collectors.joining(",")) + "}";
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Class<?>[] parameterTypes = Arrays.stream(fields).map(Field::getType).toArray(Class[]::new);

        json = json.substring(1, json.length() - 1);
        Map<String, String> fieldsMap = new HashMap<>();
        Arrays.stream(json.split(",")).forEach(pair -> putKeyValuePair(pair, fieldsMap));
        Object[] parameters = Arrays.stream(fields)
                .map(field -> parseValue(field.getType(), fieldsMap.get(field.getName())))
                .toArray();

        Constructor<T> constructor = ReflectionUtils.getConstructor(clazz, parameterTypes);
        return ReflectionUtils.newInstance(constructor, parameters);
    }

    @Override
    public <T> String toJsonList(List<T> objects) {
        return "[" + objects.stream()
                .map(this::toJson)
                .collect(Collectors.joining(",")) + "]";
    }

    @Override
    public <T> List<T> fromJsonList(String json, Class<T> clazz) {
        Pattern pattern = Pattern.compile("(\\{[^}]*})+");
        Matcher matcher = pattern.matcher(json);
        return matcher.results()
                .map(MatchResult::group)
                .map(group -> fromJson(group, clazz))
                .toList();
    }

    private String formatKeyValuePair(String key, Object value) {
        return String.format("\"%s\":\"%s\"", key, value.toString());
    }

    private void putKeyValuePair(String pair, Map<String, String> map) {
        Pattern pattern = Pattern.compile("\"([^\"]+)\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(pair);
        if(!matcher.matches()) throw new IllegalArgumentException("Invalid key/value pair: " + pair);
        String key = matcher.group(1);
        String value = matcher.group(2);
        map.put(key, value);
    }

    private <T> Object parseValue(Class<T> clazz, String value) {
        if(clazz.equals(LocalDateTime.class))
            return LocalDateTime.parse(value);
        if(clazz.equals(long.class))
            return Long.parseLong(value);
        if(clazz.isEnum())
            return Enum.valueOf((Class<Enum>) clazz, value);
        return clazz.cast(value);
    }
}
