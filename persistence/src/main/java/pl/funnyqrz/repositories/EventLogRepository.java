package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.EventLogEntity;

import java.math.BigInteger;

public interface EventLogRepository extends JpaRepository<EventLogEntity, BigInteger> {

}
