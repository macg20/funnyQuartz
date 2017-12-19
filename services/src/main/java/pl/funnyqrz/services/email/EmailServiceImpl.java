package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.pdf.PDFReportRenderer;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Service
public class EmailServiceImpl extends AbstractService implements EmailService {

    private static final String SUBJECT = "Daily Exchange Rate Report";
    private static final String ATTACHMENT_PREFIX = "report_exchange_rate";
    private static final String ATTACHMENT_SUFFIX = ".reports";

    private JavaMailSender javaMailSender;
    private PDFReportRenderer pdfReportRenderer;
    private MessageService messageService;

    @Value("${email.from}")
    private String from;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, PDFReportRenderer pdfReportRenderer, MessageService messageService) {
        this.javaMailSender = javaMailSender;
        this.pdfReportRenderer = pdfReportRenderer;
        this.messageService = messageService;
    }

    public void sendMessage(MimeMessage message) {
        javaMailSender.send(message);
    }


    @Override
    public void sendMessage(String subject, String content, Collection<String> receivers, Collection<File> attachments) throws IOException, MessagingException {
        MimeMessage message = messageService.createMessage(subject, content, "", attachments);
        receivers.forEach(receiver -> {
            try {
                message.setRecipients(Message.RecipientType.TO, receiver);
                sendMessage(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

}
