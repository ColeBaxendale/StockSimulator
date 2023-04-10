package BackEnd;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Account implements Serializable{

	private String username;
	private String password;
	private String name;
	private Portfolio portfolio;
	
	public Account(String email, String password, String name, Portfolio portfolio) {
		this.username = email;
		this.password = password;
		this.name = name;
		this.portfolio = portfolio;		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	
	
	
	
	
	
	
}
