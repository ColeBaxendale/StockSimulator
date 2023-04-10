package BackEnd;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;




public class DataBase {
	
	private static Hashtable<String, Account> accountTable = new Hashtable<String, Account>();
	
	AccountInstance account = AccountInstance.getInstance(); 
	

    private DataBase(){
    	
    }

    private static class SingletonHelper {
        private static final DataBase INSTANCE = new DataBase();
    }

    public static DataBase getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    
    @SuppressWarnings("unchecked")
	public static DataBase load() {
		try (ObjectInputStream ois = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(new File(System.getenv("LOCALAPPDATA")+"/data.dat"))))) {
			accountTable = (Hashtable<String, Account>) ois.readObject();
			System.out.println("load");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			getInstance();
			System.out.println("error load");
		}
		return getInstance();
	}
	
	public static void save() {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(new File(System.getenv("LOCALAPPDATA") + "/data.dat"))))) {
			oos.writeObject(accountTable);
			System.out.println("Save");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error Save");
		}
	}
	
	
	public boolean addAccount(Account accountToAdd) {
		if(!accountTable.containsKey(accountToAdd.getUsername())) {
			accountTable.put(accountToAdd.getUsername(), accountToAdd);
			save();
			System.out.println("Account Added!");
			return true;
		}
		System.out.println("Account Already Exists!");
		return false;
	}
	
	
	public boolean getAccount(String email, String password) {
		if(accountTable.containsKey(email)) {
			account.setUser(accountTable.get(email));
			if(account.getUser().getPassword().equals(password))
				return true;
		}
		account = AccountInstance.getInstance();
		return false;
	}
    

	
	
	
	
	

}
