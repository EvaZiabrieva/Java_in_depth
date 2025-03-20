package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsoupTest {

	private static final int  MAX_CELLS_IN_RAW = 4;

	public static List<CurrencyDTO> GetUSDCoursec()
	{
		List<CurrencyDTO> bankCurrencyInfo = new ArrayList<CurrencyDTO>();
		try{
			Document doc = Jsoup.connect("https://minfin.com.ua/ua/currency/banks/usd/").get();
			Element table = doc
					.getElementById("smTable")
					.getElementsByTag("tbody").first();

			for (Element tr : table.select("tr"))
			{
				Stream<Element> text = tr.select("td").stream().limit(MAX_CELLS_IN_RAW);
				int index = 0;
				CurrencyDTO dto = new CurrencyDTO();
				dto.Currency = "USD";
				bankCurrencyInfo.add(dto);

				for(Element el : text.toList())
				{
					if(el.text() == null || el.text().isEmpty() || el.text().isBlank()){
						bankCurrencyInfo.remove(dto);
						break;
					}

					switch (index)
					{
						case 0: dto.BankName = el.text();
							break;
						case 1: dto.Purchase = Float.parseFloat(el.text());
							break;
						case 2:
							break;
						case 3: dto.Sale =  Float.parseFloat(el.text());
							break;
					}
					index++;
				}
			}
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}

		return bankCurrencyInfo;
	}
}

