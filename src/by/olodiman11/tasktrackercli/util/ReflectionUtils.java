package by.olodiman11.tasktrackercli.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum ReflectionUtils {
    ;
    public static Object getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get field: " + field.getName(), e);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            String parameters = "(" + Arrays.stream(parameterTypes)
                    .map(Class::toString)
                    .collect(Collectors.joining(", ")) + ")";
            throw new RuntimeException("Constructor not found: " + parameters, e);
        }
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... parameters) {
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Class<? extends T> classForName(String name) {
        try {
            return (Class<? extends T>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + name, e);
        }
    }

    public static Object invokeMethod(Method method, Object object, Object... parameters) {
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getCause().getMessage(), e);
        }
    }
}
