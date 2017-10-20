package pl.funnyqrz.wrappers;

public class EmailAddress {

   private String value;

    public EmailAddress(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
