package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.ReportEntity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

public interface ReportRepository extends JpaRepository<ReportEntity, BigInteger> {

    Set<ReportEntity> findReportEntitiesByCreateDateIsGreaterThanEqualAndCreateDateIsLessThanEqual(LocalDate firstDate, LocalDate secondDate);

    ReportEntity findReportEntityByCreateDate(LocalDate date);
}
