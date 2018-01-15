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
import pl.funnyqrz.exceptions.NotFoundDatabaseRecord;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.account.UserService;
import pl.funnyqrz.services.email.EmailService;
import pl.funnyqrz.services.reports.PDFReportRenderer;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@Aspect
@Service
public class EmailAspectService extends AbstractService {

    private EmailService emailService;
    private PDFReportRenderer pdfReportRenderer;
    private UserService userService;

    @Autowired
    public EmailAspectService(EmailService emailService, PDFReportRenderer pdfReportRenderer, UserService userService) {
        this.emailService = emailService;
        this.pdfReportRenderer = pdfReportRenderer;
        this.userService = userService;
    }


    @Before("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void logBeforeDownloadAndSaveExchangeRate(JoinPoint joinPoint) {
        getLogger().info("Executing method:" + joinPoint.getSignature().getName());
    }


    @AfterReturning(value = "execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))", returning = "result")
    public void generateReportAfterDownloadedExchangeRateAndSendEmialForUsers(JoinPoint joinPoint, Object result) throws IOException, MessagingException {
        getLogger().error("Start aspect..");
        File report = pdfReportRenderer.renderReport((ExchangeRateEntity) result);
        Set<File> attachments = Sets.newHashSet(report);
        Set<String> emailAddresses = findAllEmails();
        //TODO save reports in db
        //TODO render content
        emailService.sendMessage("TEST", "TEST", emailAddresses, attachments);
    }

    private Set<String> findAllEmails() {
        Set<String> emails = userService.findAllEmails();
        if (!CollectionUtils.isEmpty(emails))
            return emails;
        else
            throw new NotFoundDatabaseRecord("Set of emails is empty!");
    }
}
