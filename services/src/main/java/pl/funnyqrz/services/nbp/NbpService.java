package pl.funnyqrz.services.nbp;


import pl.funnyqrz.entities.ExchangeRateEntity;

public interface NbpService {

    boolean echo();

    void downloadAndSaveExchangeRate();

}
