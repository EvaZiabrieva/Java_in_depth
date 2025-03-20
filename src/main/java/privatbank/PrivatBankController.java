package privatbank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class PrivatBankController {
    private final PrivatBankService privatBankService;

    public PrivatBankController(PrivatBankService privatBankService) {
        this.privatBankService = privatBankService;
    }

    @GetMapping("/exchange")
    public String getExchangeRates(@RequestParam(defaultValue = "") String date, Model model) {
        if(date.isBlank()) {
            date = GetCurrentDate();
        }
        var response = privatBankService.getExchangeRates(date);
        model.addAttribute("date", response.getDate());
        model.addAttribute("exchangeRates", response.getExchangeRates());
        return "exchange";
    }

    private String GetCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.now().format(formatter);
    }
}