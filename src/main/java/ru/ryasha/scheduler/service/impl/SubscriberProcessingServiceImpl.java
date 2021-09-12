package ru.ryasha.scheduler.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ryasha.scheduler.dto.MessageRequestDto;
import ru.ryasha.scheduler.dto.PingResponseDto;
import ru.ryasha.scheduler.feign.MessageSenderClient;
import ru.ryasha.scheduler.feign.SubscriberPingClient;
import ru.ryasha.scheduler.model.UnavailableSubscriber;
import ru.ryasha.scheduler.dto.PingStatus;
import ru.ryasha.scheduler.service.SubscriberProcessingService;
import ru.ryasha.scheduler.service.UnavailableSubscriberService;

@Service
@Log4j2
public class SubscriberProcessingServiceImpl implements SubscriberProcessingService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM hh:mm:ss");

    private final SubscriberPingClient pingClient;
    private final MessageSenderClient senderClient;
    private final UnavailableSubscriberService subscriberService;

    @Value("${scheduler.message-sender.messageTemplate}")
    private String messageTemplate;

    @Autowired
    public SubscriberProcessingServiceImpl(SubscriberPingClient pingClient,
                                           MessageSenderClient senderClient,
                                           UnavailableSubscriberService subscriberService) {
        this.pingClient = pingClient;
        this.senderClient = senderClient;
        this.subscriberService = subscriberService;
    }

    @Override
    public boolean pingSubscriber(UnavailableSubscriber subscriber) {
        PingResponseDto pingResponseDto;

        try {
            pingResponseDto = pingClient.pingSubscriber(subscriber.getMsisdnB());
        } catch (Exception ex) {
            log.error("Ping failed.", ex);
            return false;
        }

        return PingStatus.SUCCESS == PingStatus.fromStatus(pingResponseDto.getStatus());
    }

    @Override
    public boolean sendMessageToCaller(UnavailableSubscriber subscriber) {
        String messageText = String.format(messageTemplate, DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        MessageRequestDto messageDto = new MessageRequestDto(subscriber.getMsisdnA(), subscriber.getMsisdnB(),
                messageText);
        try {
            senderClient.sendMessage(messageDto);
        } catch (Exception ex) {
            log.error("Sending failed.", ex);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}<br>
     * 1) Выполняется ping-запрос:<br>
     * 1.1) Запрос завершился успешно, переход к шагу 2;<br>
     * 1.2) Запрос завершился неудачей, выполняется сохранение данных. Конец сценария;<br>
     * 2) Выполняется отправка сообщения:<br>
     * 2.1) Запрос завершился успешно. Конец сценария;<br>
     * 2.2) Запрос завершился неудачей, выполняется сохранение данных. Конец сценария.<br>
     */
    @Override
    public void performInitialProcessing(UnavailableSubscriber subscriber) {
        if (!pingSubscriber(subscriber)) {
            log.debug("Initial process: unsuccessful ping. Saving subscriber...");
            subscriberService.saveUnavailableSubscriber(subscriber);
            return;
        }

        if (!sendMessageToCaller(subscriber)) {
            log.debug("Initial process: message wasn't sent. Saving subscriber...");
            subscriber.setSuccessfulPing(true);
            subscriberService.saveUnavailableSubscriber(subscriber);
        }
    }
}
