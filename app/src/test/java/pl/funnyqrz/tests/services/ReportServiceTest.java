package pl.funnyqrz.tests.services;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.mappers.dto.ReportDto;
import pl.funnyqrz.repositories.ReportRepository;
import pl.funnyqrz.services.reports.ReportService;
import pl.funnyqrz.tests.AbstractTest;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportServiceTest extends AbstractTest {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportService reportService;

    @Test
    public void findAllReportsTest() throws SQLException {

        //given
        List<ReportEntity> reports = prepareData();

        //when
        reportRepository.save(reports);

        //then
        Set<ReportDto> expected = reportService.findAllReport();

        assertThat(expected).isNotEmpty();
        assertThat(expected.size()).isEqualTo(2);

    }

    private List<ReportEntity> prepareData() throws SQLException {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName("test1");
        reportEntity.setCreateDate(LocalDate.now());
        reportEntity.setFileContent(new SerialBlob(new byte[5]));

        ReportEntity reportEntity1 = new ReportEntity();
        reportEntity1.setFileName("test2");
        reportEntity1.setCreateDate(LocalDate.now());
        reportEntity1.setFileContent(new SerialBlob(new byte[5]));

        return Arrays.asList(reportEntity, reportEntity1);
    }
}
