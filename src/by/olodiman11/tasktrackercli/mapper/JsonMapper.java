package by.olodiman11.tasktrackercli.mapper;

import java.util.List;

public interface JsonMapper {
    <T> String toJson(T object);
    <T> T fromJson(String json, Class<T> clazz);
    <T> String toJsonList(List<T> objects);
    <T> List<T> fromJsonList(String json, Class<T> clazz);
}
