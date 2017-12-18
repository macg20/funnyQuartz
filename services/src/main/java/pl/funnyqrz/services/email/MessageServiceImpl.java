package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.pdf.PDFReportRenderer;
import pl.funnyqrz.utils.NameUtils;
import pl.funnyqrz.wrappers.EmailAddress;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

@Service
public class MessageServiceImpl extends AbstractService implements  MessageService{

    private JavaMailSender javaMailSender;
    private PDFReportRenderer pdfReportRenderer;

    @Value("${email.from}")
    private String from;

    @Autowired
    public MessageServiceImpl(JavaMailSender javaMailSender, PDFReportRenderer pdfReportRenderer) {
        this.javaMailSender = javaMailSender;
        this.pdfReportRenderer = pdfReportRenderer;
    }

    public MimeMessage createMessageWithReport(String subject,String description, Set<EmailAddress> receivers, File report) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(receivers.stream().map(x-> x.getValue()).toArray(String[]::new));
            helper.setSubject(subject);
            helper.setText(description);

            helper.addAttachment(NameUtils.createAttachmentReportName(), report);
        } catch (MessagingException e) {
            getLogger().error("Error while createing message!", e);
        }
        return message;
    }

    @Override
    public String createMessageBodyByVelocity(ExchangeRateEntity exchangeRateEntity) {
        return null;
    }

    @Override
    public String createMessageBodyByFreeMarker(ExchangeRateEntity exchangeRateEntity) {
        return null;
    }
}
