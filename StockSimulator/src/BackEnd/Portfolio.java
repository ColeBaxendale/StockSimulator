package BackEnd;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import BackEnd.StockBackEnd.OwnedStock;
import BackEnd.StockBackEnd.Stock;

public class Portfolio implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private double balance;
	private double startingBalance;
	private double buyingPower;
	private double percentGainLoss;
	private Hashtable<String, OwnedStock> ownedStocks;
	
	public Portfolio(double startingBalance) {
		this.startingBalance = startingBalance;
		balance = startingBalance;
		ownedStocks = new Hashtable<String, OwnedStock>();
		setPercentGainLoss(round(((balance - startingBalance) / startingBalance) * 100.0));
		this.buyingPower = startingBalance;
	}
	
	public String getStock(String ticker) {
		return ownedStocks.get(ticker).toString();
	}
	
	

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getStartingBalance() {
		return startingBalance;
	}
	
	public void setStartingBalance(double startingBalance) {
		this.startingBalance = startingBalance;
	}

	public Hashtable<String, OwnedStock> getOwnedStocks() {
		return ownedStocks;	
	}
	
	private double round(double d) {
		return Math.round(d * 100.00) / 100.00;
	}

	public double getBuyingPower() {
		return round(buyingPower);
	}

	public void setBuyingPower(double buyingPower) {
		this.buyingPower = buyingPower;
	}

	public double getPercentGainLoss() {
		return percentGainLoss;
	}

	public void setPercentGainLoss(double percentGainLoss) {
		this.percentGainLoss = percentGainLoss;
	}
	
	public boolean buyShares(Stock stock, int shares) {
		double costToBuy = stock.getCurrPrice() * shares;
		
		if(costToBuy > buyingPower)
			return false;
		else if(ownedStocks.containsKey(stock.getTicker())) {
			OwnedStock temp = ownedStocks.get(stock.getTicker());
			
			temp.setTotalInvested(round(temp.getTotalInvested() + costToBuy ));
			double totalInvested =  temp.getTotalInvested(); 
			
			temp.setStock(stock);
			temp.setShares(temp.getShares() + shares);
			
			temp.setAveragePrice(round(totalInvested / temp.getShares()));
			temp.setPercentGainLoss(round((((stock.getCurrPrice() * temp.getShares()-totalInvested)) / totalInvested) * 100.00));
			buyingPower = round(buyingPower - costToBuy);
			return true;
		}
		ownedStocks.put(stock.getTicker(), new OwnedStock(stock,shares));
		buyingPower = round(buyingPower - costToBuy);
		return true;
	}
	
	public boolean sellStock(String ticker, int shares) {
		OwnedStock stock = ownedStocks.get(ticker);
		if(shares > stock.getShares())
			return false;
		balance += round((stock.getStock().getCurrPrice() * shares));
		buyingPower += round((stock.getStock().getCurrPrice() * shares));
		stock.setShares(stock.getShares() - shares);
		stock.setTotalInvested(round(stock.getAveragePrice() * stock.getShares()));
		if(stock.getShares() == 0)
			ownedStocks.remove(ticker);
		return true;
	}
	
	
	public void deposit(double amount) {
		balance = round(balance + amount);
		buyingPower = round(buyingPower + amount);
		startingBalance = round(startingBalance + amount);
	}
	

	public void refresh() {
		double ownedStockBalance = 0;
		Enumeration<String> stocks = ownedStocks.keys();
		 while (stocks.hasMoreElements()) {
			 OwnedStock temp = ownedStocks.get(stocks.nextElement());
			 temp.refresh();
			 ownedStockBalance += temp.getValue();
		 }
		 balance = round(ownedStockBalance + buyingPower);
		 percentGainLoss = round(((balance - startingBalance) / startingBalance ) * 100.0);
		 
		
	}
	
	
	
	
	
	
	
	

	
}
