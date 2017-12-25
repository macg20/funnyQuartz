package pl.funnyqrz.services.email;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.wrappers.EmailAddress;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

@Service
public interface MessageService {

    MimeMessage createMessage(String subject, String content, Collection<File> attachments);

}
