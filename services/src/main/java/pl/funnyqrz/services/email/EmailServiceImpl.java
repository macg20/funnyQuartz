package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.funnyqrz.entities.account.UserEntity;
import pl.funnyqrz.exceptions.ApplicationException;
import pl.funnyqrz.services.AbstractService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

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

        MimeMessage message = messageService.createMessage(subject, content, attachments);
        receivers.stream()
                .map(this::convertStringToInternetAddress)
                .collect(Collectors.toList())
                .forEach(internetAddress -> {
                    setMessageReceiver(message, internetAddress);
                    sendMessage(message);

                });

    }

    @Override
    public void sendActivationAccountMessage(UserEntity user) {
        throw new NotImplementedException();
    }

    private void setMessageReceiver(MimeMessage message, InternetAddress receiver) {
        try {
            message.setRecipient(Message.RecipientType.TO, receiver);
        } catch (MessagingException ex) {
            getLogger().error("Occurred error in method [setMessageReceiver]", ex);
            throw new ApplicationException(ex);
        }
    }

    private InternetAddress convertStringToInternetAddress(String address) {
        try {
            return new InternetAddress(address);
        } catch (AddressException e) {
            throw new ApplicationException(e);
        }
    }

}
