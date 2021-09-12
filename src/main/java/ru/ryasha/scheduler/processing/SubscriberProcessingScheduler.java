package ru.ryasha.scheduler.processing;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ryasha.scheduler.model.UnavailableSubscriber;
import ru.ryasha.scheduler.service.SubscriberProcessingService;
import ru.ryasha.scheduler.service.UnavailableSubscriberService;

/**
 * Компонент с методами-обработчиками записей абонентов, работающих по расписаниям.
 */
@EnableScheduling
@Component
@Log4j2
public class SubscriberProcessingScheduler {

    private final UnavailableSubscriberService subscriberService;
    private final SubscriberProcessingService processingService;

    @Autowired
    public SubscriberProcessingScheduler(
            UnavailableSubscriberService subscriberService,
            SubscriberProcessingService processingService
    ) {
        this.subscriberService = subscriberService;
        this.processingService = processingService;
    }

    /**
     * Выполняет ping-запросы по расписанию, указанному в конфигурации.
     */
    @Scheduled(fixedDelayString = "#{T(Long).parseLong(${scheduler.ping.minutes}) * 60 * 1000}")
    public void pingSubscribers() {
        List<UnavailableSubscriber> subscribers = subscriberService.findNotPingedSubscribers();

        if (subscribers.size() == 0) {
            return;
        }

        log.info("Performing the ping process...");
        subscribers.forEach(subscriber -> {
            if (processingService.pingSubscriber(subscriber)) {
                subscriber.setSuccessfulPing(true);
            }
        });

        subscriberService.saveAllSubscribers(subscribers);
        log.info("Processed subscribers: " + subscribers.size());
    }

    /**
     * Выполняет отправку сообщений звонящим по расписанию, указанному в конфигурации.
     */
    @Scheduled(cron = "${scheduler.message-sender.cron}")
    public void sendMessagesToCallers() {
        List<UnavailableSubscriber> subscribers = subscriberService.findPingedSubscribers();
        List<Long> subscribersToRemove = new ArrayList<>();

        if (subscribers.size() == 0) {
            return;
        }

        log.info("Sending messages...");
        subscribers.forEach(subscriber -> {
            if (processingService.sendMessageToCaller(subscriber)) {
                subscribersToRemove.add(subscriber.getId());
            }
        });

        subscriberService.deleteSubscribersById(subscribersToRemove);
        log.info("Messages successfully sent: " + subscribersToRemove.size());
    }

    /**
     * Выполняет чистку данных в хранилище по расписанию, указанному в конфигурации.
     */
    @Scheduled(cron = "${scheduler.purge.cron}")
    public void performDataPurge() {
        log.info("Removing old data...");
        subscriberService.deleteOldData();
        log.info("Old data has been removed.");
    }
}
