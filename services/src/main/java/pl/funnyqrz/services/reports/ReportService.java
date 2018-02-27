package pl.funnyqrz.services.reports;

import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.mapper.dto.ReportContentDto;
import pl.funnyqrz.mapper.dto.ReportDto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

public interface ReportService {

    ReportEntity save(ReportEntity reportEntity);

    Set<ReportDto> findAllReport();

    ReportDto findReportByDate(LocalDate date);

    Set<ReportDto> findReportBetweenDate(LocalDate startDate, LocalDate endDate);

    ReportDto findLatestReport();

    ReportContentDto findReportContentById(BigInteger id);

}
