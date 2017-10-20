package pl.funnyqrz.services.aop;

import com.itextpdf.text.DocumentException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.exceptions.NotFoundDatabaseRecord;
import pl.funnyqrz.repositories.ExchangeRateRepository;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.email.EmailService;
import pl.funnyqrz.services.email.MessageService;
import pl.funnyqrz.services.pdf.PDFReportRenderer;
import pl.funnyqrz.utils.NameUtils;
import pl.funnyqrz.wrappers.EmailAddress;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Aspect
@Service
public class EmailAspectService extends AbstractService {

    private EmailService emailService;
    private MessageService messageService;
    private ExchangeRateRepository exchangeRateRepository;
    private PDFReportRenderer pdfReportRenderer;


    @Before("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void logBeforeAllMethodsJobExecute1(JoinPoint joinPoint) {
        getLogger().info("Executing method:" + joinPoint.getSignature().getName());
    }
    //TODO - afer executing downloadAndSaveExchangeRate method-> send email with reports( for example, velocity, fremarker template)

    @Transactional
    @After("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void sendEmailWithReportAfterDownloadAndSaveExchangeRate(JoinPoint joinPoint) {

        File report = null;
        ExchangeRateEntity lastExchangeRateEntity = null;

        try {

             lastExchangeRateEntity = findLastExchangeRateEntity();

        } catch (NotFoundDatabaseRecord notFoundDatabaseRecord) {
            getLogger().error("Error occured!", notFoundDatabaseRecord);
        }

        try {

            report = pdfReportRenderer.renderReport();

        } catch (FileNotFoundException e) {
            getLogger().error("Error occured!", e);
        } catch (DocumentException e) {
            getLogger().error("Error occured!", e);
        }
        // TODO user register and get users addresses

        Set<EmailAddress> addresses = new HashSet<>();
        addresses.add(new EmailAddress("aaaa@gmail.com"));

        MimeMessage mimeMessage = messageService.createMessageWithReport(NameUtils.createDefaultMessageTitleWithDate(),
                "DESCRIPTION",addresses, report);

        emailService.sendMessage(mimeMessage);

    }

    private ExchangeRateEntity findLastExchangeRateEntity() throws NotFoundDatabaseRecord {
        Optional<ExchangeRateEntity> exchangeRateEntity = Optional.ofNullable(exchangeRateRepository.findLast());
        return exchangeRateEntity.orElseThrow(() -> new NotFoundDatabaseRecord("Not found Exchange Rate Entity"));
    }
}
