package pl.funnyqrz.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.funnyqrz.services.AbstractService;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl extends AbstractService implements EmailService {


    private JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(MimeMessage message) {

        javaMailSender.send(message);
    }


}
