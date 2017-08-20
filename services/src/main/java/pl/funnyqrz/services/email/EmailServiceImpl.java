package pl.funnyqrz.services.email;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.pdf.PDFReportRenderer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Set;

@Service
public class EmailServiceImpl extends AbstractService implements EmailService {

    private static final String SUBJECT = "Daily Exchange Rate Report";
    private static final String ATTACHMENT_PREFIX = "report_exchange_rate";
    private static final String ATTACHMENT_SUFFIX = ".pdf";

    private JavaMailSender javaMailSender;
    private PDFReportRenderer pdfReportRenderer;

    @Value("${email.from}")
    private String from;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, PDFReportRenderer pdfReportRenderer) {
        this.javaMailSender = javaMailSender;
        this.pdfReportRenderer = pdfReportRenderer;
    }

    public MimeMessage createMessage(Set<String> receivers) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(receivers.stream().toArray(String[]::new));
            helper.setSubject(SUBJECT);
            helper.setText("Daily report!");

            helper.addAttachment(createAttachmentFileName(), pdfReportRenderer.renderReport());
        } catch (MessagingException e) {
            getLogger().error("Error while createing message!", e);
        } catch (FileNotFoundException e) {
            getLogger().error("Error while renedering report!", e);
        } catch (DocumentException e) {
            getLogger().error("Error while create pdf document!", e);
        }

        return message;
    }

    @Override
    public void sendMessage(MimeMessage message) {

        javaMailSender.send(message);
    }

    private String createAttachmentFileName() {
        return String.format("%s_%s%s", ATTACHMENT_PREFIX, LocalDate.now().toString().replaceAll("-", "_"), ATTACHMENT_SUFFIX);
    }


}
