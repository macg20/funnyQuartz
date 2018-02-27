package pl.funnyqrz.rest.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.funnyqrz.mapper.dto.ReportContentDto;
import pl.funnyqrz.mapper.dto.ReportDto;
import pl.funnyqrz.services.reports.ReportService;

import java.math.BigInteger;
import java.util.Set;

@RestController
@RequestMapping("/reports")
public class ReportRestService {

    private ReportService reportService;

    @Autowired
    public ReportRestService(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/")
    public Set<ReportDto> reports() {
        return reportService.findAllReport();
    }

    @GetMapping("/content/{reportId}")
    public ReportContentDto reportContentById(@PathVariable("reportId") BigInteger reportId) {
        return reportService.findReportContentById(reportId);
    }
}
