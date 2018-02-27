package pl.funnyqrz.services.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.mapper.ReportContentMapper;
import pl.funnyqrz.mapper.ReportMapper;
import pl.funnyqrz.mapper.dto.ReportContentDto;
import pl.funnyqrz.mapper.dto.ReportDto;
import pl.funnyqrz.repositories.ReportRepository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;
    private ReportMapper reportMapper;
    private ReportContentMapper reportContentMapper;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, ReportMapper reportMapper, ReportContentMapper reportContentMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.reportContentMapper = reportContentMapper;
    }

    @Override
    public ReportEntity save(ReportEntity reportEntity) {
        return reportRepository.save(reportEntity);
    }


    @Override
    public Set<ReportDto> findAllReport() {
        return reportRepository.findAll().stream().map(s -> reportMapper.toDto(s)).collect(Collectors.toSet());
    }

    @Override
    public ReportDto findReportByDate(LocalDate date) {
        return reportMapper.toDto(reportRepository.findReportEntityByCreateDate(date));
    }

    @Override
    public Set<ReportDto> findReportBetweenDate(LocalDate startDate, LocalDate endDate) {
        return reportRepository.findReportEntitiesByCreateDateIsGreaterThanEqualAndCreateDateIsLessThanEqual(startDate, endDate).stream().map(s -> reportMapper.toDto(s)).collect(Collectors.toSet());
    }

    @Override
    public ReportDto findLatestReport() {
        return null;
    }

    @Override
    public ReportContentDto findReportContentById(BigInteger id) {
        return reportContentMapper.toDto(reportRepository.findReportEntityById(id));
    }


}
