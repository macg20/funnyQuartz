package pl.funnyqrz.services.email;

import org.springframework.stereotype.Service;
import pl.funnyqrz.entities.account.UserEntity;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Service
public interface EmailService {

    void sendMessage(String subject, String content, Collection<String> receivers, Collection<File> attachments) throws IOException, MessagingException;

    void sendActivationAccountMessage(UserEntity user);
}
