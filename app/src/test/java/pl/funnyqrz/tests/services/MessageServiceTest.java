package pl.funnyqrz.tests.services;

import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mariadb.jdbc.Driver;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.funnyqrz.configuration.DevelopmentDatabaseConfiguration;
import pl.funnyqrz.exceptions.ApplicationException;
import pl.funnyqrz.services.email.MessageService;
import pl.funnyqrz.tests.AbstractTest;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class MessageServiceTest extends AbstractTest {

    private final static String DUMMY_SUBJECT = "DummySubject";
    private final static String DUMMY_CONTENT = "DummyContent";

    @Mock
    MessageService messageService;

    protected static final int DB_PORT = 3310;

    @Test
    public void createMessageTest() throws IOException, MessagingException {

        //given
        String dummySubject = prepareDummySubject();
        String dummyContent = prepareDummuContent();

        //when
        MessageService messageMockService = Mockito.mock(MessageService.class);
        when(messageService.createMessage(dummySubject, dummyContent, Collections.emptyList()))
                .thenReturn(prepareDummyMimeMessage());
        MimeMessage dummyMessage = messageService.createMessage(dummySubject, dummyContent, Collections.emptyList());

        assertThat(dummyMessage).isNotNull();
        assertThat((String) dummyMessage.getContent()).isEqualTo(DUMMY_CONTENT);
        assertThat(dummyMessage.getSubject()).isEqualTo(DUMMY_SUBJECT);
        assertThat(dummyMessage.getAllRecipients()).isNullOrEmpty();

    }

    @Test
    public void createMessageWithException() throws IOException, MessagingException {

        when(messageService.createMessage(null, null, null)).thenThrow(ApplicationException.class);

        assertThrows(ApplicationException.class, () -> messageService.createMessage(null, null, null));
    }

    private String prepareDummySubject() {
        return "DummySubject";
    }

    private String prepareDummuContent() {
        return "DummyContent";
    }

    private MimeMessage prepareDummyMimeMessage() throws MessagingException {
        Session session = null;
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setSubject(prepareDummySubject());
        mimeMessage.setText(prepareDummuContent());
        return mimeMessage;
    }


}
