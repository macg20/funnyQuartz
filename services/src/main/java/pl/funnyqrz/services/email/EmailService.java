package pl.funnyqrz.services.email;

import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

@Service
public interface EmailService {

    void sendMessage(MimeMessage message);

}
