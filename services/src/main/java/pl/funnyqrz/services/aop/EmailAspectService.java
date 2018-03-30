package pl.funnyqrz.services.aop;


import com.google.common.collect.Sets;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.enums.EventLogType;
import pl.funnyqrz.exceptions.ApplicationException;
import pl.funnyqrz.exceptions.NotFoundDatabaseRecord;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.account.UserService;
import pl.funnyqrz.services.email.EmailService;
import pl.funnyqrz.services.eventlog.EventLogService;
import pl.funnyqrz.services.reports.PDFReportRenderer;
import pl.funnyqrz.services.reports.ReportService;
import pl.funnyqrz.utils.resource.FilesUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Aspect
@Service
public class EmailAspectService extends AbstractService {

    private EmailService emailService;
    private PDFReportRenderer pdfReportRenderer;
    private UserService userService;
    private ReportService reportService;
    private EventLogService eventLogService;

    @Autowired
    public EmailAspectService(EmailService emailService, PDFReportRenderer pdfReportRenderer, UserService userService, ReportService reportService, EventLogService eventLogService) {
        this.emailService = emailService;
        this.pdfReportRenderer = pdfReportRenderer;
        this.userService = userService;
        this.reportService = reportService;
        this.eventLogService = eventLogService;
    }

    @Before("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void logBeforeDownloadAndSaveExchangeRate(JoinPoint joinPoint) {
        getLogger().info("Executing method:" + joinPoint.getSignature().getName());
    }


    @AfterReturning(value = "execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))", returning = "result")
    public void generateReportAfterDownloadedExchangeRateAndSendEmialForUsers(JoinPoint joinPoint, Object result) throws IOException, MessagingException {

        File report = pdfReportRenderer.renderReport((ExchangeRateEntity) result);
        reportService.save(createReportEntity(report));

        try {
            Set<String> emailAddresses = findAllEmails();
            Set<File> attachments = Sets.newHashSet(report);
            emailService.sendMessage("TEST", "TEST", emailAddresses, attachments);
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            eventLogService.registerEvent(e.getMessage(), LocalDateTime.now(), EventLogType.ERROR);

        }
        report.deleteOnExit();
    }

    private Set<String> findAllEmails() {
        Set<String> emails = userService.findAllEmails();
        if (!CollectionUtils.isEmpty(emails))
            return emails;
        throw new NotFoundDatabaseRecord("Set of emails is empty!");
    }

    private ReportEntity createReportEntity(File pdfReport) {
        try {
            ReportEntity report = new ReportEntity();
            report.setCreateDate(LocalDate.now());
            report.setFileName(pdfReport.getName());
            report.setFileContent(FilesUtils.fileToByteArray(pdfReport));
            return report;
        } catch (Exception e) {
            getLogger().error("Cannot create report entity", e);
            throw new ApplicationException("Cannot create report entity", e);
        }
    }
}
