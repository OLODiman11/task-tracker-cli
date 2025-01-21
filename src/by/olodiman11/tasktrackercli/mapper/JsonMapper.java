package by.olodiman11.tasktrackercli.mapper;

public interface JsonMapper {
    <T> String toJson(T object);
    <T> T fromJson(String json, Class<T> clazz);
}
