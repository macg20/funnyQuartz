package pl.funnyqrz.messages;

public class GenericMessage<T> {

    private T messages;

    public GenericMessage() {

    }

    public GenericMessage(T messages) {
        this.messages = messages;
    }

    public T getMessages() {
        return messages;
    }

    public void setMessages(T messages) {
        this.messages = messages;
    }
}
