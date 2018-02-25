package pl.funnyqrz.enums;

public enum CurrencyEnum {

    CHF("CHF"),
    USD("USD"),
    EUR("EUR"),
    GBP("GBP");

    String name;

    CurrencyEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
