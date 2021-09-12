package ru.ryasha.scheduler.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Транспортный объект с информацией о недоступном абоненте.
 */
@RequiredArgsConstructor
@Getter
public class UnavailableSubscriberDto {

    /**
     * Номер звонившего
     */
    @NotNull
    private final String msisdnA;

    /**
     * Номер, на который производился звонок, когда он был недоступен.
     */
    @NotNull
    private final String msisdnB;
}
