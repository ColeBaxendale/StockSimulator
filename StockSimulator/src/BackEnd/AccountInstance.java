package BackEnd;

import java.io.Serializable;

public final class AccountInstance implements Serializable{
	  

	private static final long serialVersionUID = 1L;
	
	private Account account;
	  private final static AccountInstance INSTANCE = new AccountInstance();
	  
	  private AccountInstance() {}
	  
	  public static AccountInstance getInstance() {
	    return INSTANCE;
	  }
	  
	  public void setUser(Account u) {
	    this.account = u;
	  }
	  
	  public Account getUser() {
	    return this.account;
	  }
	}