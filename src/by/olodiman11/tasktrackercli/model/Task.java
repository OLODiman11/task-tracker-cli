package by.olodiman11.tasktrackercli.model;

import by.olodiman11.tasktrackercli.enums.TaskStatus;

import java.time.LocalDateTime;

public record Task(
        long id,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
