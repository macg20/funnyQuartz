package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.pdf.PDFReportRenderer;
import pl.funnyqrz.utils.FileNameUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

public class MessageServiceImpl extends AbstractService implements  MessageService{

    private static final String SUBJECT = "Daily Exchange Rate Report";

    private JavaMailSender javaMailSender;
    private PDFReportRenderer pdfReportRenderer;

    @Value("${email.from}")
    private String from;

    @Autowired
    public MessageServiceImpl(JavaMailSender javaMailSender, PDFReportRenderer pdfReportRenderer) {
        this.javaMailSender = javaMailSender;
        this.pdfReportRenderer = pdfReportRenderer;
    }

    public MimeMessage createMessageWithReport(String title, String description, Set<String> receivers, File report) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(receivers.stream().toArray(String[]::new));
            helper.setSubject(SUBJECT);
            helper.setText(description);

            helper.addAttachment(FileNameUtils.createAttachmentReportName(), report);
        } catch (MessagingException e) {
            getLogger().error("Error while createing message!", e);
        }
        return message;
    }
}
