import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public void addRegularAccount(String quickId, double quickApr, String accountType) {
		if (accountType.equalsIgnoreCase("CheckingAccount")) {
			accounts.put(quickId, new CheckingAccount(quickId, quickApr));
		} else if (accountType.equalsIgnoreCase("SavingsAccount")) {
			accounts.put(quickId, new SavingsAccount(quickId, quickApr));

		}
	}

	public void addCDAccount(String quickId, double quickApr, double quickInitialBalance) {
		accounts.put(quickId, new CDAccount(quickId, quickApr, quickInitialBalance));
	}

	public int getSize() {
		return accounts.size();
	}

	public boolean accountExistsById(String quickId) {
		if (accounts.get(quickId) != null) {
			return true;
		}
		return false;
	}
}
