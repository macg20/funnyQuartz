package pl.funnyqrz.services.email;

import pl.funnyqrz.utils.message.Message;

public interface EmailService {

    void sendMessage(Message message);
}
