package ru.ryasha.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryasha.scheduler.dto.UnavailableSubscriberDto;
import ru.ryasha.scheduler.mapper.EntityMapper;
import ru.ryasha.scheduler.model.UnavailableSubscriber;
import ru.ryasha.scheduler.service.SubscriberProcessingService;

/**
 * Предоставляет маппинг для регистрации недоступных абонентов.
 */
@RestController
@RequestMapping("/unavailableSubscriber")
public class UnavailableSubscriberRegistrationController {

    private final SubscriberProcessingService processingService;
    private final EntityMapper<UnavailableSubscriber, UnavailableSubscriberDto> entityMapper;

    @Autowired
    public UnavailableSubscriberRegistrationController(
            SubscriberProcessingService processingService,
            EntityMapper<UnavailableSubscriber, UnavailableSubscriberDto> entityMapper
    ) {
        this.processingService = processingService;
        this.entityMapper = entityMapper;
    }

    /**
     * Метод для регистрации недоступных абонентов.
     *
     * @param dto транспортный объект с информацией о недоступном абоненте.
     */
    @PostMapping
    public ResponseEntity<String> registerUnavailableSubscriber(@RequestBody UnavailableSubscriberDto dto) {
        processingService.performInitialProcessing(entityMapper.mapToEntity(dto));

        return ResponseEntity.ok("OK");
    }
}
