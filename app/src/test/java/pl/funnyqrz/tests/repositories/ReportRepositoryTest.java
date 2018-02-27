package pl.funnyqrz.tests.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.repositories.ReportRepository;
import pl.funnyqrz.tests.AbstractTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class ReportRepositoryTest extends AbstractTest {

    @Autowired
    ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        reportRepository.deleteAll();
    }

    @Test
    public void returnEmptyCollection() {

        // when
        List<ReportEntity> reports = reportRepository.findAll();

        //then
        assertThat(reports).isNotEqualTo(null);
        assertThat(reports).isEmpty();

    }

}
