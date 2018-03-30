package pl.funnyqrz.enums;

public enum EventLogType {

    INFO("INFO"),
    ERROR("ERROR");

    private String type;

    EventLogType(String type) {
        this.type = type;
    }
}
