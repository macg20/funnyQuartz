package pl.funnyqrz.services.eventlog;


import pl.funnyqrz.entities.EventLogEntity;

import java.util.Collection;

public interface EventLogService {

    void save(EventLogEntity eventLogEntity);

    Collection<EventLogEntity> findAll();

    Collection<EventLogEntity> findLast100rows();
}