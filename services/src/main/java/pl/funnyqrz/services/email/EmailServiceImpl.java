package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.pdf.PDFReportRenderer;
import pl.funnyqrz.utils.FileNameUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

@Service
public class EmailServiceImpl extends AbstractService implements EmailService {


    private JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(MimeMessage message) {

        javaMailSender.send(message);
    }


}
