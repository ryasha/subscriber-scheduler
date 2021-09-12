package ru.ryasha.scheduler.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ryasha.scheduler.model.UnavailableSubscriber;

/**
 * Репозиторий записей о недоступных абонентах.
 */
public interface UnavailableSubscriberRepository extends JpaRepository<UnavailableSubscriber, Long> {

    /**
     * Получает все записи, имеющие неудачный статус ping-запроса.
     */
    List<UnavailableSubscriber> findAllBySuccessfulPingIsFalse();

    /**
     * Получает все записи, имеющие успешный статус ping-запроса.
     *
     * @param pageable ограничитель количества читаемых записей.
     * @return список записей, имеющие успешный статус ping-запроса.
     */
    List<UnavailableSubscriber> findAllBySuccessfulPingIsTrue(Pageable pageable);

    /**
     * Удаляет записи, которые старше указанной даты.
     *
     * @param beforeDate дата-маркер. Записи, которые старше этой даты, подлежат удалению.
     */
    void deleteUnavailableSubscribersByCreationTimeBefore(LocalDateTime beforeDate);

    /**
     * Удаляет записи с указанными ID.
     *
     * @param ids список ID к удалению.
     */
    void deleteByIdIn(List<Long> ids);
}
