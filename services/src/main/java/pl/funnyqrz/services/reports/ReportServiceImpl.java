package pl.funnyqrz.services.reports;

import org.springframework.stereotype.Service;
import pl.funnyqrz.entities.ReportEntity;

import java.time.LocalDate;
import java.util.Set;

@Service
public class ReportServiceImpl implements ReportService {


    @Override
    public void save(ReportEntity reportEntity) {

    }

    @Override
    public Set<ReportEntity> findAllReport() {
        return null;
    }

    @Override
    public ReportEntity findReportByDate(LocalDate date) {
        return null;
    }

    @Override
    public Set<ReportEntity> findReportBetweenDate(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public ReportEntity findLatestReport() {
        return null;
    }
}
