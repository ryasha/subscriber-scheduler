package ru.ryasha.scheduler.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ryasha.scheduler.dto.MessageRequestDto;

/**
 * Клиент для отправки сообщений абонентам.
 */
@FeignClient(value = "message-sender", url = "http://${scheduler.message-sender.requestUrl}")
public interface MessageSenderClient {

    /**
     * Выполняет отправку сообщения абоненту.
     *
     * @param messageDto информация по сообщению.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    void sendMessage(@RequestBody MessageRequestDto messageDto);
}
