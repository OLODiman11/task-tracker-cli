package by.olodiman11.tasktrackercli.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface ReflectionUtils {

    static Object getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get field: " + field.getName(), e);
        }
    }

    static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            String parameters = "(" + Arrays.stream(parameterTypes)
                    .map(Class::toString)
                    .collect(Collectors.joining(", ")) + ")";
            throw new RuntimeException("Constructor not found: " + parameters, e);
        }
    }

    static <T> T newInstance(Constructor<T> constructor, Object... parameters) {
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
