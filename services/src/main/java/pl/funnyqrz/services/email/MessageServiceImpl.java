package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.funnyqrz.exceptions.ApplicationException;
import pl.funnyqrz.services.AbstractService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Collection;

@Service
public class MessageServiceImpl extends AbstractService implements MessageService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from}")
    private String from;

    @Autowired
    public MessageServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public MimeMessage createMessage(String subject, String content, Collection<File> attachments) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
//        Multipart multiPart = new MimeMultipart("alternative");
//
//        MimeBodyPart textPart = new MimeBodyPart();
//        textPart.setText(content, "utf-8");
//
//        MimeBodyPart footerPart = new MimeBodyPart();
//        textPart.setText(footer, "utf-8");
//
//        MimeBodyPart attachmentPart = new MimeBodyPart();
//        attachments.forEach(file -> {
//            try {
//                attachmentPart.attachFile(file);
//            } catch (IOException e) {
//                getLogger().error("Cannot add attachment");
//            } catch (MessagingException e) {
//                getLogger().error("Cannot add attachment");
//            }
//        });

//        multiPart.addBodyPart(textPart);
//        multiPart.addBodyPart(footerPart);
//        multiPart.addBodyPart(attachmentPart);
//        message.setContent(multiPart);
        message.setSubject(subject);
        message.setText(content);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        attachments.forEach(file ->{
            try {
                mimeMessageHelper.addAttachment(file.getName(),file);
            } catch (MessagingException e) {
                throw new ApplicationException("Cannot add attachment");
            }
        });
        return message;
    }
}
