package pl.funnyqrz.services.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.repositories.ExchangeRateRepository;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.email.EmailService;
import pl.funnyqrz.services.email.MessageService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collections;

@Aspect
@Service
public class EmailAspectService extends AbstractService {

    private EmailService emailService;
    private MessageService messageService;
    private ExchangeRateRepository exchangeRateRepository;
//    private PDFReportRenderer pdfReportRenderer;

    @Autowired
    public EmailAspectService(EmailService emailService, MessageService messageService, ExchangeRateRepository exchangeRateRepository) {
        this.emailService = emailService;
        this.messageService = messageService;
        this.exchangeRateRepository = exchangeRateRepository;
//        this.pdfReportRenderer = pdfReportRenderer;
    }

    @Before("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void logBeforeAllMethodsJobExecute1(JoinPoint joinPoint) {
        getLogger().info("Executing method:" + joinPoint.getSignature().getName());
    }
    //TODO - afer executing downloadAndSaveExchangeRate method-> send email with reports( for example, velocity, fremarker template)

    @Transactional
    @After("execution(* pl.funnyqrz.services.nbp.NbpServiceImpl.downloadAndSaveExchangeRate(..))")
    public void sendEmailWithReportAfterDownloadAndSaveExchangeRate(JoinPoint joinPoint) throws IOException, MessagingException {

//        File report = null;
//        ExchangeRateEntity lastExchangeRateEntity = null;
//
//        try {
//
//             lastExchangeRateEntity = findLastExchangeRateEntity();
//
//        } catch (NotFoundDatabaseRecord notFoundDatabaseRecord) {
//            getLogger().error("Error occured!", notFoundDatabaseRecord);
//        }
//
//        try {
//
//            report = pdfReportRenderer.renderReport(lastExchangeRateEntity);
//
//        } catch (FileNotFoundException e) {
//            getLogger().error("Error occurred!", e);
//        } catch (DocumentException e) {
//            getLogger().error("Error occurred!", e);
//        }
//        // TODO user register and get users addresses
//
//        Set<EmailAddress> addresses = new HashSet<>();
//        addresses.add(new EmailAddress("aaaaaa@gmail.com"));
//
//        MimeMessage mimeMessage = messageService.createMessageWithReport(NameUtils.createDefaultMessageTitleWithDate(),
//                "DESCRIPTION",addresses, report);
//
//        emailService.sendMessage(mimeMessage);

        emailService.sendMessage ("TEST","TEST",Collections.emptyList(), Collections.emptyList());
    }

//    private ExchangeRateEntity findLastExchangeRateEntity() throws NotFoundDatabaseRecord {
//        Optional<ExchangeRateEntity> exchangeRateEntity = Optional.ofNullable(exchangeRateRepository.findLast());
//        return exchangeRateEntity.orElseThrow(() -> new NotFoundDatabaseRecord("Not found echange rate in database"));
//    }
}
