package pl.funnyqrz.services.email;

import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Collection;

@Service
public interface MessageService {

    MimeMessage createMessage(String recipient, String subject, String content, Collection<File> attachments);
}
