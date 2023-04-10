package BackEnd.StockBackEnd;

import java.io.Serializable;

public class Stock extends WebScrapper implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ticker;
	private double currPrice;
	
	
	public Stock(String ticker){
		this.ticker = ticker;
		this.currPrice = getCurrPrice(ticker);
	}


	public double getCurrPrice(String ticker) {
		return getCurrentPrice(ticker);
	}
	
	public void getCurrPriceRefresh() {
		currPrice = getCurrentPrice(ticker);
	}
	
	
	public String getTicker() {
		return ticker;
	}


	public void setTicker(String ticker) {
		this.ticker = ticker;
	}


	public double getCurrPrice() {
		return currPrice;
	}


	public void setCurrPrice(double currPrice) {
		this.currPrice = currPrice;
	}
	
	

}
