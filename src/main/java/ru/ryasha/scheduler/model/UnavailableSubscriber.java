package ru.ryasha.scheduler.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность с информацией по недоступному абоненту.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class UnavailableSubscriber implements Serializable {

    /**
     * Идентификатор записи.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    /**
     * Номер звонящго.
     */
    private String msisdnA;

    /**
     * Номер, на который производился звонок, когда он был недоступен.
     */
    private String msisdnB;

    /**
     * Время создания записи.
     */
    private LocalDateTime creationTime;

    /**
     * Флаг успешности ping-запроса.
     */
    private boolean successfulPing;

    public UnavailableSubscriber(String msisdnA, String msisdnB, LocalDateTime creationTime) {
        this.msisdnA = msisdnA;
        this.msisdnB = msisdnB;
        this.creationTime = creationTime;
        this.successfulPing = false;
    }
}
