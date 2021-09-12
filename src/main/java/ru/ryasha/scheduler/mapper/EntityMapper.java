package ru.ryasha.scheduler.mapper;

/**
 * Интерфейс мапперов Entity в DTO и наоборот.
 *
 * @param <E> класс Entity.
 * @param <D> класс DTO.
 */
public interface EntityMapper<E, D> {

    /**
     * Маппинг из DTO в Entity.
     *
     * @param dto DTO с данными.
     * @return заполненная данными Entity.
     */
    E mapToEntity(D dto);

    /**
     * Маппинг из Entity в DTO.
     *
     * @param entity Entity с данными.
     * @return заполненный данными DTO.
     */
    D mapFromEntity(E entity);
}
