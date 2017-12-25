package pl.funnyqrz.tests.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.funnyqrz.exceptions.ApplicationException;
import pl.funnyqrz.services.email.MessageService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MessageServiceTest {

    private final static String DUMMY_SUBJECT = "DummySubject";
    private final static String DUMMY_CONTENT = "DummyContent";

    @Mock
    MessageService messageService;

    @Test
    public void createMessageTest() throws IOException, MessagingException {

        //given
        String dummySubject = prepareSubject();
        String dummyContent = prepareContent();

        //when
        MessageService messageMockService = Mockito.mock(MessageService.class);
        when(messageService.createMessage(dummySubject, dummyContent, Collections.emptyList()))
                .thenReturn(dummyMimeMessageWithoutException());
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

    private String prepareSubject() {
        return "DummySubject";
    }

    private String prepareContent() {
        return "DummyContent";
    }

    private MimeMessage dummyMimeMessageWithoutException() throws MessagingException {
        Session session = null;
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setSubject(prepareSubject());
        mimeMessage.setText(prepareContent());
        return mimeMessage;
    }


}
