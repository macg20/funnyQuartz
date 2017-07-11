package pl.funnyqrz.services.helpers;

import pl.funnyqrz.entities.ExchangeRateEntity;

public class ExchangeRateValidator {

    public static boolean validate(ExchangeRateEntity exchangeRateEntity) {
        return (exchangeRateEntity.getChfExchangeRate() == null && exchangeRateEntity.getEurExchangeRate() ==null
                && exchangeRateEntity.getUsdExchangeRate() ==null && exchangeRateEntity.getGbfExchangeRate() ==null);
    }
}
