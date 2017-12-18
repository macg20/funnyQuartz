package pl.funnyqrz.services.email;

import org.springframework.stereotype.Service;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.wrappers.EmailAddress;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

@Service
public interface MessageService {

    MimeMessage createMessageWithReport(String title, String description, Set<EmailAddress> receivers, File report);

    String createMessageBodyByVelocity(ExchangeRateEntity exchangeRateEntity);

    String createMessageBodyByFreeMarker(ExchangeRateEntity exchangeRateEntity);
}
