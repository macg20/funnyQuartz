package pl.funnyqrz.services.nbp;


import com.google.common.base.Strings;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.eventlog.EventLogService;
import pl.funnyqrz.utils.exceptions.EmptyHostException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.EventLogEntity;
import pl.funnyqrz.entities.ExchangeRateEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

@Service
public class NbpServiceImpl extends AbstractService implements NbpService {

    private static final String CURRENCY_CODE = "code";
    private static final String CURRENCY_USD = "USD";
    private static final String CURRENCY_EUR = "EUR";
    private static final String CURRENCY_CHF = "CHF";
    private static final String CURRENCY_GBF = "GBF";
    private static final String CURRENCY_RATES = "rates";
    private static final String CURRENCY_MID = "mid";

    @Value("${nbp.api.url}")
    private String host;
    private EventLogService eventLogService;

    @Autowired
    public NbpServiceImpl(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @Override
    @Transactional
    public ExchangeRateEntity getExchangeRate() {
        return convertStringToExchangeRateEntity(readString());
    }

    private BufferedReader getBufferedReader() throws IOException, EmptyHostException {
        if (host == null)
            throw new EmptyHostException("Host cannot be null!");

        URL urlAddress = new URL(host);
        HttpURLConnection httpsURLConnection = (HttpURLConnection) urlAddress.openConnection();
        return new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
    }

    private String readString() {

        try (BufferedReader bufferedReader = getBufferedReader()) {

            StringBuilder stringBuilder = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null)
                stringBuilder.append(temp);

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            getLogger().error("error while establish connect", e);
            eventLogService.save(new EventLogEntity("error while establish connect, class:" + getClass().toString(), LocalDate.now()));

        } catch (IOException e) {
            getLogger().error("error while establish connect", e);
            eventLogService.save(new EventLogEntity("error while establish connect, class:" + getClass().toString(), LocalDate.now()));
        } catch (EmptyHostException e) {
            getLogger().error("Host cannot be null, complete url in properties", e);
            eventLogService.save(new EventLogEntity("Host cannot be null, complete url in properties, class:" + getClass().toString(), LocalDate.now()));

        } finally {
            getLogger().info("Successful download from NBP API");
            eventLogService.save(new EventLogEntity("Successful download from NBP API:" + getClass().toString(), LocalDate.now()));

        }
        return Strings.nullToEmpty(null);
    }

    private ExchangeRateEntity convertStringToExchangeRateEntity(String value) {
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
        if (!Strings.isNullOrEmpty(value)) {
            JSONArray json = new JSONArray(value);
            JSONObject jsonObject = json.getJSONObject(0);
            JSONArray jsonArray = jsonObject.getJSONArray(CURRENCY_RATES);
            for (int i = 0; i < jsonArray.length(); i++) {
                switch (jsonArray.getJSONObject(i).get(CURRENCY_CODE).toString()) {
                    case CURRENCY_USD:
                        exchangeRateEntity.setUsdExchangeRate(jsonArray.getJSONObject(i).getBigDecimal(CURRENCY_MID));
                        break;
                    case CURRENCY_EUR:
                        exchangeRateEntity.setEurExchangeRate(jsonArray.getJSONObject(i).getBigDecimal(CURRENCY_MID));
                        break;
                    case CURRENCY_CHF:
                        exchangeRateEntity.setChfExchangeRate(jsonArray.getJSONObject(i).getBigDecimal(CURRENCY_MID));
                        break;
                    case CURRENCY_GBF:
                        exchangeRateEntity.setGbfExchangeRate(jsonArray.getJSONObject(i).getBigDecimal(CURRENCY_MID));
                        break;
                }
            }
            exchangeRateEntity.setCreateDate(LocalDate.now());

        }
        return exchangeRateEntity;
    }

}
