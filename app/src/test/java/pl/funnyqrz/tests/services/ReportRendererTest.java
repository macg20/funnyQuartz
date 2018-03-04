package pl.funnyqrz.tests.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.repositories.ReportRepository;
import pl.funnyqrz.services.reports.PDFReportRenderer;
import pl.funnyqrz.tests.AbstractTest;
import pl.funnyqrz.utils.resource.FilesUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportRendererTest extends AbstractTest {

    @Autowired
    PDFReportRenderer pdfReportRenderer;

    @Autowired
    ReportRepository reportRepository;

    @Test
    public void renderPdfTest() throws IOException, SQLException {

        // given
        ExchangeRateEntity dummyExchnageRate = prepareDummyExchangeRateEntity();

        // when
        File dummyReportFile = pdfReportRenderer.renderReport(dummyExchnageRate);
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileContent(FilesUtils.fileToBlob(dummyReportFile));
        reportEntity.setFileName(dummyReportFile.getName());
        reportEntity.setCreateDate(LocalDate.now());
        reportEntity = reportRepository.saveAndFlush(reportEntity);
        //then

        assertThat(reportEntity).isNotNull();
        assertThat(reportEntity.getId()).isNotNull();
        assertThat(reportEntity.getId()).isGreaterThan(BigInteger.ZERO);

        //then
        assertThat(dummyReportFile).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void saveReportInDatabaseTest() throws IOException, SQLException {

        // given
        ExchangeRateEntity dummyExchnageRate = prepareDummyExchangeRateEntity();

        // when
        File dummyReportFile = pdfReportRenderer.renderReport(dummyExchnageRate);
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileContent(FilesUtils.fileToBlob(dummyReportFile));
        reportEntity.setFileName(dummyReportFile.getName());
        reportEntity.setCreateDate(LocalDate.now());
        reportEntity = reportRepository.saveAndFlush(reportEntity);
        //then

        assertThat(reportEntity).isNotNull();
        assertThat(reportEntity.getId()).isNotNull();
        assertThat(reportEntity.getId()).isGreaterThan(BigInteger.ZERO);
    }

    private ExchangeRateEntity prepareDummyExchangeRateEntity() {
        ExchangeRateEntity dummyExchnageRate = new ExchangeRateEntity();
        dummyExchnageRate.setChfExchangeRate(BigDecimal.ONE);
        dummyExchnageRate.setEurExchangeRate(BigDecimal.TEN);
        dummyExchnageRate.setGbpExchangeRate(BigDecimal.ONE);
        dummyExchnageRate.setUsdExchangeRate(BigDecimal.TEN);
        dummyExchnageRate.setCreateDate(LocalDateTime.now());
        return dummyExchnageRate;
    }


}
