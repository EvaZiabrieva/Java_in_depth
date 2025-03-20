package privatbank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRatesResponse {
    private String date;
    private String bank;
    private int baseCurrency;
    private String baseCurrencyLit;

    @JsonProperty("exchangeRate")
    private List<ExchangeRate> exchangeRates;

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public int getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(int baseCurrency) { this.baseCurrency = baseCurrency; }

    public String getBaseCurrencyLit() { return baseCurrencyLit; }
    public void setBaseCurrencyLit(String baseCurrencyLit) { this.baseCurrencyLit = baseCurrencyLit; }

    public List<ExchangeRate> getExchangeRates() { return exchangeRates; }
    public void setExchangeRates(List<ExchangeRate> exchangeRates) { this.exchangeRates = exchangeRates; }
}
