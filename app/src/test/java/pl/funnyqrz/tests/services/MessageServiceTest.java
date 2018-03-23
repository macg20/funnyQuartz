package pl.funnyqrz.tests.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.funnyqrz.exceptions.ApplicationException;
import pl.funnyqrz.services.email.MessageService;
import pl.funnyqrz.tests.AbstractTest;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
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
        String dummyContent = prepareDummyContent();
        String dummyEmail = prepareDummyEmail();
        //when
        MessageService messageMockService = Mockito.mock(MessageService.class);
        when(messageService.createMessage(dummyEmail,dummySubject, dummyContent, Collections.emptyList()))
                .thenReturn(prepareDummyMimeMessage());
        MimeMessage dummyMessage = messageService.createMessage(dummyEmail,dummySubject, dummyContent, Collections.emptyList());

        assertThat(dummyMessage).isNotNull();
        assertThat((String) dummyMessage.getContent()).isEqualTo(DUMMY_CONTENT);
        assertThat(dummyMessage.getSubject()).isEqualTo(DUMMY_SUBJECT);
        assertThat(dummyMessage.getAllRecipients()).isNullOrEmpty();

    }

    @Test
    public void createMessageWithException() {

        when(messageService.createMessage(null,null, null, null)).thenThrow(ApplicationException.class);

        assertThrows(ApplicationException.class, () -> messageService.createMessage(null, null, null, null));
    }

    private String prepareDummySubject() {
        return "DummySubject";
    }

    private String prepareDummyContent() {
        return "DummyContent";
    }

    private String prepareDummyEmail() {
        return "xyz@xyz";
    }

    private MimeMessage prepareDummyMimeMessage() throws MessagingException {
        Session session = null;
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setSubject(prepareDummySubject());
        mimeMessage.setText(prepareDummyContent());
        return mimeMessage;
    }


}
