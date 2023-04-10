package BackEnd.StockBackEnd;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public abstract class WebScrapper{
		
	String ticker;
	
	double getCurrentPrice(String ticker){
		double priceDouble = 0;
		final String url = "https://finance.yahoo.com/quote/"+ticker+"?p="+ticker+"&.tsrc=fin-srch";
		try {
			final Document document = Jsoup.connect(url).get();
			Elements price = document.getElementById("quote-header-info").getElementsByClass("Fw(b) Fz(36px) Mb(-4px) D(ib)");
			priceDouble = Double.valueOf(price.text());
		
		} catch (NullPointerException | IOException e) {
			return 0;
		}
		
		
		
		
		return round(priceDouble);
		
	}
	
	
	
	private double round(double d) {
		return Math.round(d * 100.00) / 100.00;
	}
	
}
