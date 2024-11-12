import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	protected Map<String, Account> getAccounts() {
		return accounts;
	}

	protected void addRegularAccount(String quickId, double quickApr, String accountType) {
		if (accountType.equalsIgnoreCase("CheckingAccount")) {
			accounts.put(quickId, new CheckingAccount(quickId, quickApr));
		} else if (accountType.equalsIgnoreCase("SavingsAccount")) {
			accounts.put(quickId, new SavingsAccount(quickId, quickApr));

		}
	}

	protected void addCDAccount(String quickId, double quickApr, double quickInitialBalance) {
		accounts.put(quickId, new CDAccount(quickId, quickApr, quickInitialBalance));
	}

	protected int getSize() {
		return accounts.size();
	}

	protected boolean accountExistsById(String quickId) {
		if (accounts.get(quickId) != null) {
			return true;
		}
		return false;
	}
}
