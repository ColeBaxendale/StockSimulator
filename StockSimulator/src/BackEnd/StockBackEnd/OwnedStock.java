package BackEnd.StockBackEnd;

import java.io.Serializable;

public class OwnedStock extends WebScrapper implements Serializable {

	private static final long serialVersionUID = 1L;
	private Stock stock;
	private int shares;
	private double averagePrice;
	private double totalInvested; 
	private double percentGainLoss;
	private double value;

	public OwnedStock(Stock stock, int shares) {
		this.stock = stock;
		this.shares = shares;
		totalInvested = round(stock.getCurrPrice() * shares);
		averagePrice = round(totalInvested / shares);
		percentGainLoss = 0;
		value = totalInvested;
	}
	
	public OwnedStock(Stock stock, int shares, double averagePrice, double totalInvested, double percentGainLoss) {
		super();
		this.stock = stock;
		this.shares = shares;
		this.averagePrice = round(averagePrice);
		this.totalInvested = round(totalInvested);
		this.percentGainLoss = round(percentGainLoss);
	}


	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public double getTotalInvested() {
		return totalInvested;
	}

	public void setTotalInvested(double totalInvested) {
		this.totalInvested = totalInvested;
	}

	public double getPercentGainLoss() {
		return percentGainLoss;
	}

	public void setPercentGainLoss(double percentGainLoss) {
		this.percentGainLoss = percentGainLoss;
	}
	
	
	
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void refresh() {
		stock.getCurrPriceRefresh();
		value = round(stock.getCurrPrice() * shares);
		percentGainLoss = round(((value - totalInvested) / totalInvested) * 100.00);
		
	}


	private double round(double d) {
		return Math.round(d * 100.00) / 100.00;
	}
	
	@Override
	public String toString() {
		String plusMinus = "";
		if(!(percentGainLoss < 0)) 
			plusMinus += "+";

		return stock.getTicker().toUpperCase() + " $" + stock.getCurrPrice() + ", Shares: " + shares 
				+ "    Total Value: $" + value + "      +/- " + plusMinus +percentGainLoss + "%        AvgCost: $" + averagePrice;
	}

}
