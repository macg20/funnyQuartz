package pl.funnyqrz.messages;

import java.util.HashSet;
import java.util.Set;

public class GenericMessage<T> {

    private Set<T> messages;



    public GenericMessage() {
        if(messages == null)
            messages = new HashSet<>();
    }

    public GenericMessage(Set<T> messages) {
        this.messages = messages;
    }

    public Set<T> getMessages() {
        return messages;
    }

    public void setMessages(Set<T> messages) {
        this.messages = messages;
    }
}
