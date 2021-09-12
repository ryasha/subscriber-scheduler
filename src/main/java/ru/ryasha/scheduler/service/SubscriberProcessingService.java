package ru.ryasha.scheduler.service;

import ru.ryasha.scheduler.model.UnavailableSubscriber;

/**
 * Сервис, предосталвяющий методы для процессинга абонентов.
 */
public interface SubscriberProcessingService {

    /**
     * Выполняет ping-запрос недоступного абонента.
     *
     * @param subscriber информация об абонентах.
     * @return true, если запрос завершился успешно. Иначе - false.
     */
    boolean pingSubscriber(UnavailableSubscriber subscriber);

    /**
     * Отправляет сообщение на номер вызывающего абонента.
     *
     * @param subscriber информация об абонентах.
     * @return true, если запрос завершился успешно. Иначе - false.
     */
    boolean sendMessageToCaller(UnavailableSubscriber subscriber);

    /**
     * Выполняет начальную обработку поступившего запроса.
     *
     * @param subscriber информация об абонентах.
     */
    void performInitialProcessing(UnavailableSubscriber subscriber);
}
