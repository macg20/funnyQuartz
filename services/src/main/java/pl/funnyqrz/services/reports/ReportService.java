package pl.funnyqrz.services.reports;

import org.springframework.cglib.core.Local;
import pl.funnyqrz.entities.ReportEntity;

import java.time.LocalDate;
import java.util.Set;

public interface ReportService {

    void save(ReportEntity reportEntity);

    Set<ReportEntity> findAllReport();

    ReportEntity findReportByDate(LocalDate date);

    Set<ReportEntity> findReportBetweenDate(LocalDate startDate, LocalDate endDate);

    ReportEntity findLatestReport();


}
