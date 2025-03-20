package com.example.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;



@Controller
public class CurrencyController {

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String sort) {
        List<CurrencyDTO> rates = JsoupTest.GetUSDCoursec();

        if ("asc".equals(sort)) {
            rates = rates.stream().sorted((r1, r2) -> Float.compare(r1.Purchase, r2.Purchase)).collect(Collectors.toList());
        } else if ("desc".equals(sort)) {
            rates = rates.stream().sorted((r1, r2) -> Float.compare(r2.Purchase, r1.Purchase)).collect(Collectors.toList());
        }

        model.addAttribute("rates", rates);
        return "index";
    }

    @GetMapping("/export")
    public void ExportToExcel(HttpServletResponse response) {
        ExcelExportService service = new ExcelExportService();

        //TODO: cache parsed table
        try {
            service.exportToExcel(response, JsoupTest.GetUSDCoursec());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
