package pl.funnyqrz.enums;

public enum EventLogTypeEnum {

    INFO("INFO"),
    ERROR("ERROR");

    private String type;

    EventLogTypeEnum(String type) {
        this.type = type;
    }
}
