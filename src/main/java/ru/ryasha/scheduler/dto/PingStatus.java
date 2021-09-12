package ru.ryasha.scheduler.dto;

import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечисление возможных статусов ping-запроса.
 */
@RequiredArgsConstructor
public enum PingStatus {

    SUCCESS("inNetwork"),
    FAILURE("unavailableSubscriber")
    ;

    @Getter
    private final String status;

    /**
     * Получение {@link PingStatus} по строковому коду.
     */
    public static PingStatus fromStatus(String value) {
        return Stream.of(values()).filter(s -> s.getStatus().equals(value)).findFirst().orElse(FAILURE);
    }
}
