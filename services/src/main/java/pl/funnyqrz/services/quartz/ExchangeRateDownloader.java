package pl.funnyqrz.services.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.nbp.NbpService;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ExchangeRateDownloader extends AbstractService implements Job {

    @Autowired
    private NbpService nbpService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        getLogger().info("[Quartz] Download from NBP Api " + LocalTime.now() + " " + LocalDate.now());
        nbpService.downloadAndSaveExchangeRate();
    }
}
