package pl.funnyqrz.services.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.exchangerate.ExchangeRateService;
import pl.funnyqrz.services.nbp.NbpService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ExchangeRateDownloader extends AbstractService implements Job {

    @Autowired
    private NbpService nbpService;
    @Autowired
    private ExchangeRateService exchangeRateService;


    public void downloadAndSave() {
        ExchangeRateEntity exchangeRateEntity = nbpService.getExchangeRate();
        exchangeRateService.save(exchangeRateEntity);

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        getLogger().info("[Quartz] Download from NBP Api " + LocalDateTime.now());
        downloadAndSave();
        getLogger().info("[Quartz] Saved exchange rate");
    }
}
