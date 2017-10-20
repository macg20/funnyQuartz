package pl.funnyqrz.services.email;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

public interface MessageService {

    MimeMessage createMessageWithReport(String title, String descritpion, Set<String> receivers, File report);

}
