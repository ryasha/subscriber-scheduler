package ru.ryasha.scheduler.service;

import java.util.List;
import ru.ryasha.scheduler.model.UnavailableSubscriber;

/**
 * Сервис, предоставляющий методы для работы с записями {@link UnavailableSubscriber}.
 */
public interface UnavailableSubscriberService {

    /**
     * Добавляет запись в хранилище.
     *
     * @param subscriber объект {@link UnavailableSubscriber}.
     */
    void saveUnavailableSubscriber(UnavailableSubscriber subscriber);

    /**
     * Добавляет в хранилище список записей.
     *
     * @param subscribers список объектов {@link UnavailableSubscriber}.
     */
    void saveAllSubscribers(List<UnavailableSubscriber> subscribers);

    /**
     * Удаляет записи по их ID.
     *
     * @param ids список ID записей.
     */
    void deleteSubscribersById(List<Long> ids);

    /**
     * Удаляет "устаревшие" данные.
     */
    void deleteOldData();

    /**
     * @return список абонентов, ping которых не прошел.
     */
    List<UnavailableSubscriber> findNotPingedSubscribers();

    /**
     * @return список абонентов, ping которых прошел.
     */
    List<UnavailableSubscriber> findPingedSubscribers();
}
