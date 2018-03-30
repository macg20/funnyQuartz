package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.funnyqrz.entities.EventLogEntity;

import java.math.BigInteger;
import java.util.Set;

public interface EventLogRepository extends JpaRepository<EventLogEntity, BigInteger> {

    @Query(value ="SELECT id, description, date, type FROM event_logs order by id desc limit 100", nativeQuery = true)
    Set<EventLogEntity> findLast100Events();

}
