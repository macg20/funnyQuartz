package pl.funnyqrz.services.aop;

import com.google.common.collect.Sets;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.repositories.ExchangeRateRepository;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.email.EmailService;
import pl.funnyqrz.services.reports.PDFReportRenderer;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@Aspect
@Service
public class EmailAspectService extends AbstractService {

    private EmailService emailService;
    private ExchangeRateRepository exchangeRateRepository;
    private PDFReportRenderer pdfReportRenderer;

    @Autowired
    public EmailAspectService(EmailService emailService, ExchangeRateRepository exchangeRateRepository,
                              PDFReportRenderer pdfReportRenderer) {
        this.emailService = emailService;
        this.exchangeRateRepository = exchangeRateRepository;
        this.pdfReportRenderer = pdfReportRenderer;
    }

    @Before("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void logBeforeAllMethodsJobExecute1(JoinPoint joinPoint) {
        getLogger().info("Executing method:" + joinPoint.getSignature().getName());
    }

    @Transactional
    @After("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void sendEmailWithReportAfterDownloadAndSaveExchangeRate(JoinPoint joinPoint) throws IOException, MessagingException {

        File report = pdfReportRenderer.renderReport(findLastExchangeRateEntity());
        Set<File> attachments = Sets.newHashSet(report);
        // TODO read emails from user repository
        Set<String> emailAddresses = Sets.newHashSet(Arrays.asList("aaa@gmail.com", "aaabbb@gmail.com",
                "bbb@gmail.com"));
        emailService.sendMessage("TEST", "TEST", emailAddresses, attachments);
    }

    private ExchangeRateEntity findLastExchangeRateEntity() {
        return exchangeRateRepository.findLast();
    }
}
