package Controllers;


import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

import BackEnd.Account;
import BackEnd.AccountInstance;
import BackEnd.DataBase;
import BackEnd.Portfolio;
import BackEnd.StockBackEnd.OwnedStock;
import BackEnd.StockBackEnd.Stock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class MainScreenController implements Initializable {

	@FXML
	private Label accountBalance;

	@FXML
	private Label helloPrompt;

	@FXML
	private Label overallPercent;

	@FXML
	private TextField stockSearch;

	@FXML
	private Label searchStockPrice;

	@FXML
	private Label currentPriceHeader;
	
	@FXML
	private Label buyingPower;

	@FXML
	private TextField sharesToBuy;
	
	@FXML
	private TextField sharesToSell;
	
	@FXML
	private TextField depositAmount;

	@FXML
	private ListView<String> ownedStocks;

	Alert error = new Alert(AlertType.ERROR);
	Alert success = new Alert(AlertType.INFORMATION);

	AccountInstance holder = AccountInstance.getInstance();

	Account account = holder.getUser();
	Portfolio portfolio = account.getPortfolio();

	private Stock currentStock;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		helloPrompt.setText("Hello " + holder.getUser().getName() + "!");
		getOwnedStocks();
		accountBalance.setText("Account Balance: $" + portfolio.getBalance());
		buyingPower.setText("Buying Power: $" + portfolio.getBuyingPower());
		if(!(portfolio.getPercentGainLoss() < 0))
			overallPercent.setText("Overall Percent: +" + portfolio.getPercentGainLoss() + "%");
		else
			overallPercent.setText("Overall Percent: " + portfolio.getPercentGainLoss() + "%");
		
		
	}
	
	public void refresh() {
		accountBalance.setText("Account Balance: $" + portfolio.getBalance());
		buyingPower.setText("Buying Power: $" + portfolio.getBuyingPower());
	}

	private void getOwnedStocks() {
		ownedStocks.getItems().clear();
		double ownedStockBalance = 0;
		Enumeration<String> keys = portfolio.getOwnedStocks().keys();
		while (keys.hasMoreElements()) {
			OwnedStock temp =  portfolio.getOwnedStocks().get(keys.nextElement());
			temp.refresh();
			ownedStockBalance += temp.getValue();
			portfolio.setBalance(round(ownedStockBalance + portfolio.getBuyingPower()));
			portfolio.setPercentGainLoss(round(((portfolio.getBalance() - portfolio.getStartingBalance()) / portfolio.getStartingBalance() ) * 100.0));
			ownedStocks.getItems().add(temp.toString());
		}
	}

	@FXML
	void searchStock(ActionEvent event) {
		if (stockSearch.getText().isBlank()) {
			error.setHeaderText("No Ticker Inputed!");
			error.setContentText("Looks like you have not entered a stock ticker :( Try Again!");
			error.showAndWait();
			stockSearch.setText("");
			return;
		}
		currentStock = new Stock(stockSearch.getText().toUpperCase());
		if (currentStock.getCurrPrice() != 0) {
			currentPriceHeader.setText(currentStock.getTicker() + " Current Price");
			String price = "";
			double currentPrice = round(currentStock.getCurrPrice());
			price += currentPrice;
			searchStockPrice.setText("$" + price);
			stockSearch.setText("");
			return;
		}
		error.setHeaderText("Stock Not Found!");
		error.setContentText("Looks like that ticker does not exist :( Try Again!");
		error.showAndWait();
		stockSearch.setText("");
		return;
	}


	@FXML
	void sellStock(ActionEvent event) {
		if(sharesToSell.getText().isBlank()) {
			error.setHeaderText("No Amount Of Shares Inputed!");
			error.setContentText("Looks like you have not entered an amount of shares you want to sell :( Try Again!");
			error.showAndWait();
			sharesToSell.setText("");
			return;
		} 
		else if (!onlyDigits(sharesToSell.getText())) {
			error.setHeaderText("Shares Inputed Is Not A Number!");
			error.setContentText(
					"Looks like you have entered an amount of shares you want to sell, but thats not a number :( Try Again!");
			error.showAndWait();
			sharesToSell.setText("");
			return;
		}
		if(ownedStocks.getSelectionModel().getSelectedItem() == null) {
			error.setHeaderText("No Stock Chosen!");
			error.setContentText("Looks like you have not chosen a stock to sell yet :( Try Again!");
			error.showAndWait();
			sharesToSell.setText("");
			return;
		}
		String stock = ownedStocks.getSelectionModel().getSelectedItem();
		if(portfolio.sellStock(stock.split(" ")[0],Integer.parseInt(sharesToSell.getText()))) {
			success.setHeaderText(stock.split(" ")[0] + " Sold!");
			success.setContentText("Congrats! You just sold " + Integer.parseInt(sharesToSell.getText()) + " shares of " +stock.split(" ")[0]);
			success.showAndWait();
			sharesToSell.setText("");
			initialize(null, null);
			return;
		}
		else {
			error.setHeaderText("Not Enough Shares!");
			error.setContentText("Looks like you have not chosen an amount of shares you do not own :( Try Again!");
			error.showAndWait();
			sharesToSell.setText("");
			return;
		}
		
		
	}

	@FXML
	void BuySearchStockShares(ActionEvent event) {
		
		if(currentStock == null) {
			error.setHeaderText("No Stock Chosen!");
			error.setContentText("Looks like you have not searched a stock yet :( Try Again!");
			error.showAndWait();
			sharesToBuy.setText("");
			return;
		}
			 
		else if (sharesToBuy.getText().isBlank()) {
			error.setHeaderText("No Amount Of Shares Inputed!");
			error.setContentText("Looks like you have not entered an amount of shares you want to buy :( Try Again!");
			error.showAndWait();
			sharesToBuy.setText("");
			return;
		} else if (!onlyDigits(sharesToBuy.getText())) {
			error.setHeaderText("Shares Inputed Is Not A Number!");
			error.setContentText(
					"Looks like you have entered an amount of shares you want to buy, but thats not a number :( Try Again!");
			error.showAndWait();
			sharesToBuy.setText("");
			return;
		}
		
		
		if(portfolio.buyShares(currentStock,Integer.parseInt(sharesToBuy.getText()))) {
			success.setHeaderText(currentStock.getTicker() + " Purchased!");
			success.setContentText("Congrats! You just purchased " + Integer.parseInt(sharesToBuy.getText()) + " shares of " +currentStock.getTicker());
			success.showAndWait();
			sharesToBuy.setText("");
			initialize(null, null);
			return;
		}
		else {
			error.setHeaderText("Looks like you do not have enough money to purchase these shares!");
			error.setContentText("Looks like you have entered an amount of shares that you cannot afford :( Try Again!");
			error.showAndWait();
			sharesToBuy.setText("");
			return;
		}

	}

	@FXML
	void Refresh(ActionEvent event) {
		initialize(null, null);
		if(currentStock != null) {
			currentStock.getCurrPriceRefresh();
			currentPriceHeader.setText(currentStock.getTicker() + " Current Price");
			String price = "";
			double currentPrice = round(currentStock.getCurrPrice());
			price += currentPrice;
			searchStockPrice.setText("$" + price);
			stockSearch.setText("");
		}
		
	}
	
	@FXML
	void Exit(ActionEvent event) {
		DataBase.save();
		System.exit(0);
		
	}
	
	@FXML
	void Deposit(ActionEvent event) {
		if (depositAmount.getText().isBlank()) {
			error.setHeaderText("No Amount Inputed To Deposit!");
			error.setContentText("Looks like you have not entered an amount of money you want to deposit :( Try Again!");
			error.showAndWait();
			depositAmount.setText("");
			return;
		} 
		else if (!onlyDigits(depositAmount.getText())) {
			error.setHeaderText("Money Inputed Is Not A Number!");
			error.setContentText(
					"Looks like you have entered an amount of money you want to deposit, but thats not a number :( Try Again!");
			error.showAndWait();
			depositAmount.setText("");
			return;
		}
		double depositFunds = Double.parseDouble(depositAmount.getText());
		portfolio.deposit(depositFunds);
		success.setHeaderText("You have deposited $" + depositFunds + " into your account!");
		success.setContentText("Congrats! You just deposited " + depositFunds + " into your account!");
		success.showAndWait();
		depositAmount.setText("");
		refresh();
		return;
		
		
		
	}

	public static boolean onlyDigits(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)) && i != str.length() - 3)
				return false;
		}
		return true;
	}

	private double round(double d) {
		return Math.round(d * 100.00) / 100.00;
	}

}
