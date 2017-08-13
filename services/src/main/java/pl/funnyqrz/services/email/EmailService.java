package pl.funnyqrz.services.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendMessage(SimpleMailMessage message);
}
