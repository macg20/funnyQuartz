package pl.funnyqrz.services.email;

import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public interface EmailService {

    void sendMessage(MimeMessage message);
}
