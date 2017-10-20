package pl.funnyqrz.services.email;

import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Set;

@Service
public interface EmailService {

    void sendMessage(MimeMessage message);

    MimeMessage createMessageWithReport(String title, String descritpion, Set<String> receivers);
}
