package ru.ryasha.scheduler.mapper.impl;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import ru.ryasha.scheduler.dto.UnavailableSubscriberDto;
import ru.ryasha.scheduler.mapper.EntityMapper;
import ru.ryasha.scheduler.model.UnavailableSubscriber;

/**
 * Реализация маппига из {@link UnavailableSubscriber} в {@link UnavailableSubscriberDto} и обратно.
 */
@Component
public class UnavailableSubscriberMapper implements EntityMapper<UnavailableSubscriber, UnavailableSubscriberDto> {

    public UnavailableSubscriber mapToEntity(UnavailableSubscriberDto dto) {
        return new UnavailableSubscriber(dto.getMsisdnA(), dto.getMsisdnB(), LocalDateTime.now());
    }

    @Override
    public UnavailableSubscriberDto mapFromEntity(UnavailableSubscriber entity) {
        return new UnavailableSubscriberDto(entity.getMsisdnA(), entity.getMsisdnB());
    }

}
