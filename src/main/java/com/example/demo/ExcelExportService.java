package com.example.demo;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExcelExportService {
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;

    public void newReportExcel() {
        workbook = new XSSFWorkbook();
    }

    public HttpServletResponse initResponseForExportExcel(HttpServletResponse response, String fileName) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        return response;
    }

    public void writeTableHeaderExcel(String sheetName, String titleName, String[] headers) {
        sheet = workbook.createSheet(sheetName);
        org.apache.poi.ss.usermodel.Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        createCell(row, 0, titleName, style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        for (int i = 0; i < headers.length; i++) {
            createCell(row, i, headers[i], style);
        }
    }

    public void createCell(org.apache.poi.ss.usermodel.Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(columnCount);
        if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public CellStyle getFontContentExcel() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        return style;
    }

    public void writeTableData(List<CurrencyDTO> data) {
        CellStyle style = getFontContentExcel();

        int startRow = 2;

        for (CurrencyDTO currencyDTO : data) {
            Row row = sheet.createRow(startRow++);
            int columnCount = 0;
            createCell(row, columnCount++, currencyDTO.BankName, style);
            createCell(row, columnCount++, currencyDTO.Purchase, style);
            createCell(row, columnCount, currencyDTO.Sale, style);
        }
    }

    public void writeStatistics(List<CurrencyDTO> data) {
        double totalPurchase = 0, totalSale = 0;
        double maxPurchase = Double.MIN_VALUE, maxSale = Double.MIN_VALUE;
        double minPurchase = Double.MAX_VALUE, minSale = Double.MAX_VALUE;

        for (CurrencyDTO currencyDTO : data) {
            totalPurchase += currencyDTO.Purchase;
            totalSale += currencyDTO.Sale;
            maxPurchase = Math.max(maxPurchase, currencyDTO.Purchase);
            minPurchase = Math.min(minPurchase, currencyDTO.Purchase);
            maxSale = Math.max(maxSale, currencyDTO.Sale);
            minSale = Math.min(minSale, currencyDTO.Sale);
        }

        double avgPurchase = totalPurchase / data.size();
        double avgSale = totalSale / data.size();

        int rowCount = sheet.getLastRowNum() + 2;
        org.apache.poi.ss.usermodel.Row row;

        row = sheet.createRow(rowCount++);
        createCell(row, 0, "Загальний закуп", getFontContentExcel());
        createCell(row, 1, totalPurchase, getFontContentExcel());

        row = sheet.createRow(rowCount++);
        createCell(row, 0, "Загальний продаж", getFontContentExcel());
        createCell(row, 1, totalSale, getFontContentExcel());

        row = sheet.createRow(rowCount++);
        createCell(row, 0, "Максимальний закуп", getFontContentExcel());
        createCell(row, 1, maxPurchase, getFontContentExcel());

        row = sheet.createRow(rowCount++);
        createCell(row, 0, "Мінімальний закуп", getFontContentExcel());
        createCell(row, 1, minPurchase, getFontContentExcel());

        row = sheet.createRow(rowCount++);
        createCell(row, 0, "Максимальний продаж", getFontContentExcel());
        createCell(row, 1, maxSale, getFontContentExcel());

        row = sheet.createRow(rowCount++);
        createCell(row, 0, "Мінімальний продаж", getFontContentExcel());
        createCell(row, 1, minSale, getFontContentExcel());

        row = sheet.createRow(rowCount++);
        createCell(row, 0, "Середній закуп", getFontContentExcel());
        createCell(row, 1, avgPurchase, getFontContentExcel());

        row = sheet.createRow(rowCount++);
        createCell(row, 0, "Середній продаж", getFontContentExcel());
        createCell(row, 1, avgSale, getFontContentExcel());
    }

    public void exportToExcel(HttpServletResponse response, List<CurrencyDTO> data) throws IOException {
        newReportExcel();

        if (data == null || data.isEmpty()) {
            response.getWriter().write("No data to export");
            return;
        }

        response = initResponseForExportExcel(response, "UserExcel");
        ServletOutputStream outputStream = response.getOutputStream();

        String[] headers = new String[]{"Банк", "Закупівля", "Продаж"};
        writeTableHeaderExcel("Валютний курс USD", "Курси валют", headers);

        writeTableData(data);
        writeStatistics(data);

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
