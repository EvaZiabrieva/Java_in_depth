package privatbank;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Service
public class PrivatBankService {
    private static final String API_URL = "https://api.privatbank.ua/p24api/exchange_rates?json&date=";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public PrivatBankService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public ExchangeRatesResponse getExchangeRates(String date) {
        String url = API_URL + date;
        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            return objectMapper.readValue(jsonResponse, ExchangeRatesResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка парсинга JSON", e);
        }
    }
}
