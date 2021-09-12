package ru.ryasha.scheduler.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Транспортный объект, содержащий информацию для отправки SMS-соообщения абоненту.
 */
@RequiredArgsConstructor
@Getter
public class MessageRequestDto {

    /**
     * Номер звонившего.
     */
    @NotNull
    private final String msisdnA;

    /**
     * Номер, на который производился звонок, когда он был недоступен.
     */
    @NotNull
    private final String msisdnB;

    /**
     * Текст сообщения.
     */
    @NotNull
    private final String text;
}
