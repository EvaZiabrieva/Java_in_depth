package privatbank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {
    private String baseCurrency;
    private String currency;
    private double saleRateNB;
    private double purchaseRateNB;
    private Double saleRate;
    private Double purchaseRate;

    public String getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public double getSaleRateNB() { return saleRateNB; }
    public void setSaleRateNB(double saleRateNB) { this.saleRateNB = saleRateNB; }

    public double getPurchaseRateNB() { return purchaseRateNB; }
    public void setPurchaseRateNB(double purchaseRateNB) { this.purchaseRateNB = purchaseRateNB; }

    public Double getSaleRate() { return saleRate; }
    public void setSaleRate(Double saleRate) { this.saleRate = saleRate; }

    public Double getPurchaseRate() { return purchaseRate; }
    public void setPurchaseRate(Double purchaseRate) { this.purchaseRate = purchaseRate; }
}