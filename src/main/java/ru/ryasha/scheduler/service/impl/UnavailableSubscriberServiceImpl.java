package ru.ryasha.scheduler.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.ryasha.scheduler.model.UnavailableSubscriber;
import ru.ryasha.scheduler.repository.UnavailableSubscriberRepository;
import ru.ryasha.scheduler.service.UnavailableSubscriberService;

@Service
@EnableTransactionManagement
public class UnavailableSubscriberServiceImpl implements UnavailableSubscriberService {

    private final UnavailableSubscriberRepository repository;

    /**
     * Максимальное количество записей, которое можно забрать за одну итерацию.
     */
    @Value("#{T(Integer).parseInt(${scheduler.message-sender.max-message-count})}")
    private int maxMessageCount;

    /**
     * Время жизни записей, в днях.
     */
    @Value("#{T(Integer).parseInt(${scheduler.purge.time-to-live-days})}")
    private int timeToLiveDays;

    @Autowired
    public UnavailableSubscriberServiceImpl(UnavailableSubscriberRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveUnavailableSubscriber(UnavailableSubscriber subscriber) {
        repository.save(subscriber);
    }

    @Override
    public void saveAllSubscribers(List<UnavailableSubscriber> subscribers) {
        repository.saveAll(subscribers);
    }

    @Transactional
    @Override
    public void deleteSubscribersById(List<Long> ids) {
        repository.deleteByIdIn(ids);
    }

    /**
     * {@inheritDoc}
     * Использует {@link UnavailableSubscriberServiceImpl#timeToLiveDays} для определения "устаревания".
     */
    @Override
    public void deleteOldData() {
        repository.deleteUnavailableSubscribersByCreationTimeBefore(LocalDateTime.now().minusDays(timeToLiveDays));
    }

    @Override
    public List<UnavailableSubscriber> findNotPingedSubscribers() {
        return repository.findAllBySuccessfulPingIsFalse();
    }

    /**
     * {@inheritDoc}
     * Использует {@link UnavailableSubscriberServiceImpl#maxMessageCount} для ограничения количества одновременно
     * обрабатываемых сообщений.
     */
    @Override
    public List<UnavailableSubscriber> findPingedSubscribers() {
        return repository.findAllBySuccessfulPingIsTrue(PageRequest.of(0, maxMessageCount));
    }

}
