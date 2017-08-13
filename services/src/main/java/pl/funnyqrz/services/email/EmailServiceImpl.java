package pl.funnyqrz.services.email;

import org.springframework.mail.SimpleMailMessage;
import pl.funnyqrz.services.AbstractService;

public class EmailServiceImpl extends AbstractService implements EmailService {

    @Override
    public void sendMessage(SimpleMailMessage message) {
    }
}
