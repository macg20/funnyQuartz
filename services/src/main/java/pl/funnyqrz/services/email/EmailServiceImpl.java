package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.funnyqrz.services.AbstractService;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Service
public class EmailServiceImpl extends AbstractService implements EmailService {


    private JavaMailSender javaMailSender;
    private MessageService messageService;

    @Value("${email.from}")
    private String from;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, MessageService messageService) {
        this.javaMailSender = javaMailSender;
        this.messageService = messageService;
    }

    public void sendMessage(MimeMessage message) {
        javaMailSender.send(message);
    }


    @Override
    public void sendMessage(String subject, String content, Collection<String> receivers, Collection<File> attachments) throws IOException {
        receivers.stream()
                .forEach(recipient -> {
                    sendMessage(messageService.createMessage(recipient, subject, content, attachments));
                });
    }


}
