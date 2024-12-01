package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	protected Bank() {
		accounts = new HashMap<>();
	}

	protected Map<String, Account> getAccounts() {
		return accounts;
	}

	protected void addRegularAccount(String quickId, double quickApr, String accountType) {
		if (accountType.equalsIgnoreCase("checking")) {
			accounts.put(quickId, new CheckingAccount(quickId, quickApr));
		} else if (accountType.equalsIgnoreCase("savings")) {
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

	protected void passTime(int months) {
		ArrayList<String> accountsToRemove = new ArrayList<>();
		for (int i = 0; i < months; i++) {
			for (String quickId : accounts.keySet()) {
				Account account = accounts.get(quickId);

				if (account.getBalance() == 0) {
					accountsToRemove.add(quickId);
					continue;
				}
				if (account.getBalance() < 100) {
					account.reduceBalance(25);
				}
			}
		}

		for (String quickId : accountsToRemove) {
			accounts.remove(quickId);
		}

	}
}
