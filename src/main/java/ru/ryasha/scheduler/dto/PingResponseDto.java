package ru.ryasha.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Транспортный объект с информацией о результатах ping-запроса.
 */
@AllArgsConstructor(onConstructor = @__(@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)))
@Getter
public class PingResponseDto {

    /**
     * Статус ping-запроса.
     */
    @NotNull
    private final String status;
}
