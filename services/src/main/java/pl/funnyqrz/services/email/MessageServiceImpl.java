package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.funnyqrz.exceptions.ApplicationException;
import pl.funnyqrz.services.AbstractService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Collection;

@Service
public class MessageServiceImpl extends AbstractService implements MessageService {

    private JavaMailSender javaMailSender;

    @Value("${email.from}")
    private String from;

    @Autowired
    public MessageServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public MimeMessage createMessage(String recipient, String subject, String content, Collection<File> attachments) {

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(new InternetAddress(recipient));
            helper.setSubject(subject);
            helper.setText(content);
            helper.setFrom(from);
            attachments.forEach(file ->{
                try {
                    helper.addAttachment(file.getName(),file);
                } catch (MessagingException e) {
                    throw new ApplicationException("Cannot add attachment");
                }
            });
        } catch (MessagingException e) {
            throw new ApplicationException("Cannot create message");
        }

        return message;
    }
}
