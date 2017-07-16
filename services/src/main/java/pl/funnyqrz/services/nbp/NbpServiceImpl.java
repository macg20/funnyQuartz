package pl.funnyqrz.services.nbp;


import com.google.common.base.Strings;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.EventLogEntity;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.eventlog.EventLogService;
import pl.funnyqrz.services.exchangerate.ExchangeRateService;
import pl.funnyqrz.services.helpers.ExchangeRateValidator;
import pl.funnyqrz.utils.exceptions.InvalidHostException;
import pl.funnyqrz.utils.resource.PropertiesValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

@Service
public class NbpServiceImpl extends AbstractService implements NbpService {

    private static final String CURRENCY_CODE = "code";
    private static final String CURRENCY_USD = "USD";
    private static final String CURRENCY_EUR = "EUR";
    private static final String CURRENCY_CHF = "CHF";
    private static final String CURRENCY_GBP = "GBP";
    private static final String CURRENCY_RATES = "rates";
    private static final String CURRENCY_MID = "mid";
    private static final int DEFAULT_TABLE_INDEX = 0;

    @Value("${nbp.api.url}")
    private String host;
    private EventLogService eventLogService;
    private ExchangeRateService exchangeRateService;

    @Autowired
    public NbpServiceImpl(EventLogService eventLogService, ExchangeRateService exchangeRateService) {
        this.eventLogService = eventLogService;
        this.exchangeRateService = exchangeRateService;
    }


    private ExchangeRateEntity getExchangeRate() {
        return echo() == true ? convertStringToExchangeRateEntity(readString()) : new ExchangeRateEntity();
    }

    @Override
    public boolean echo() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(host);
            HttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (IOException e) {
            getLogger().error("Cannot connet with url: " + host, e);
            eventLogService.save(new EventLogEntity("Cannot connet with url: " + host, LocalDateTime.now()));
            return false;
        }
    }

    @Override
    @Transactional
    public void downloadAndSaveExchangeRate() {
        ExchangeRateEntity exchangeRateEntity = getExchangeRate();
        if (!ExchangeRateValidator.validate(exchangeRateEntity)) {
            getLogger().info("Saving exchange rate");
            exchangeRateService.save(exchangeRateEntity);
        }
    }

    private BufferedReader getBufferedReader() throws InvalidHostException, IOException {
        if (!PropertiesValidator.isEmpty(host) || !PropertiesValidator.isValidUrl(host))
            throw new InvalidHostException("Invalid host!");

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
            getLogger().error("Error while establish connect", e);
            eventLogService.save(new EventLogEntity("error while establish connect, class:" + getClass().toString(), LocalDateTime.now()));

        } catch (IOException e) {
            getLogger().error("Error while establish connect", e);
            eventLogService.save(new EventLogEntity("Error while establish connect, class:" + getClass().toString(), LocalDateTime.now()));
        } catch (InvalidHostException e) {
            getLogger().error("Invalid host, enter a valid host in properties", e);
            eventLogService.save(new EventLogEntity("Invalid host, enter a valid host in properties, class:" + getClass().toString(), LocalDateTime.now()));

        } finally {
            getLogger().info("Successful download from NBP API");
            eventLogService.save(new EventLogEntity("Successful download from NBP API:" + getClass().toString(), LocalDateTime.now()));

        }
        return Strings.nullToEmpty(null);
    }

    private ExchangeRateEntity convertStringToExchangeRateEntity(String value) {
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
        if (!Strings.isNullOrEmpty(value)) {
            try {
                jsonToExchangeRateEntity(value, exchangeRateEntity);
            } catch (JSONException jsonException) {
                getLogger().error("Error while parsing..", jsonException);
                eventLogService.save(new EventLogEntity(jsonException.getMessage(), LocalDateTime.now()));
                return exchangeRateEntity;
            }
        }
        return exchangeRateEntity;
    }

    private void jsonToExchangeRateEntity(String value, ExchangeRateEntity exchangeRateEntity) throws JSONException {
        JSONArray json = new JSONArray(value);
        JSONObject jsonObject = json.getJSONObject(DEFAULT_TABLE_INDEX);
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
                case CURRENCY_GBP:
                    exchangeRateEntity.setGbfExchangeRate(jsonArray.getJSONObject(i).getBigDecimal(CURRENCY_MID));
                    break;
            }
        }
        exchangeRateEntity.setCreateDate(LocalDateTime.now());
    }

}
