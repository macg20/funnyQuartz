package pl.funnyqrz.services.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.repositories.ReportRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void save(ReportEntity reportEntity) {
        reportRepository.save(reportEntity);
    }


    @Override
    public Set<ReportEntity> findAllReport() {
        return new HashSet<>(reportRepository.findAll());
    }

    @Override
    public ReportEntity findReportByDate(LocalDate date) {
        return reportRepository.findReportEntityByCreateDate(date);
    }

    @Override
    public Set<ReportEntity> findReportBetweenDate(LocalDate startDate, LocalDate endDate) {
        return reportRepository.findReportEntitiesByCreateDateIsGreaterThanEqualAndCreateDateIsLessThanEqual(startDate,endDate);
    }

    @Override
    public ReportEntity findLatestReport() {
        return null;
    }
}
