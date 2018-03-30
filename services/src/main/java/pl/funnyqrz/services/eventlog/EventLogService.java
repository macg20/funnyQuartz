package pl.funnyqrz.services.eventlog;


import pl.funnyqrz.entities.EventLogEntity;
import pl.funnyqrz.enums.EventLogType;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventLogService {

    Collection<EventLogEntity> findAll();

    Collection<EventLogEntity> findLast100Events();

    void registerEvent(String description, LocalDateTime time, EventLogType type);
}