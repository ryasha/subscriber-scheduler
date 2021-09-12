package ru.ryasha.scheduler.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ryasha.scheduler.dto.PingResponseDto;

/**
 * Клиент для выполнения ping-запросов по недоступным абонентам.
 */
@FeignClient(value = "ping-server", url = "http://${scheduler.ping.requestUrl}")
public interface SubscriberPingClient {

    /**
     * Выполняет ping-запрос на указанный номер абонента.
     *
     * @param msisndB номер, на который будет выполняться запрос.
     * @return статус ping-запроса.
     */
    @GetMapping(produces = "application/json")
    @ResponseBody PingResponseDto pingSubscriber(@RequestParam("msisdn") String msisndB);
}
