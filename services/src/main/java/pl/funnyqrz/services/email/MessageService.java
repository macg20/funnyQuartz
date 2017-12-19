package pl.funnyqrz.services.email;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Service
public interface MessageService {

    MimeMessage createMessage(String subject, String content, String footer, Collection<File> attachments) throws MessagingException, IOException;
}
