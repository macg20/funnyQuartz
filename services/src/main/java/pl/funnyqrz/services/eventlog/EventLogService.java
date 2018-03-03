package pl.funnyqrz.services.eventlog;


import pl.funnyqrz.entities.EventLogEntity;
import pl.funnyqrz.enums.EventLogTypeEnum;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventLogService {

    Collection<EventLogEntity> findAll();

    Collection<EventLogEntity> findLast100rows();

    void registerEvent(String description, LocalDateTime time, EventLogTypeEnum type);
}