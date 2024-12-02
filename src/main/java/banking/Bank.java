package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {
	protected int time = 0;
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
		time += months;
		ArrayList<String> accountsToRemove = new ArrayList<>();
		for (int i = 0; i < months; i++) {
			processAccounts(accountsToRemove);
		}

		removeZeroBalanceAccounts(accountsToRemove);
	}

	private void processAccounts(ArrayList<String> accountsToRemove) {
		for (String quickId : accounts.keySet()) {
			Account account = accounts.get(quickId);
			handleAccount(account, accountsToRemove);
		}
	}

	private void handleAccount(Account account, ArrayList<String> accountsToRemove) {
		if (account.getBalance() == 0) {
			accountsToRemove.add(account.getIdValue());
			return;
		}

		if (account.getBalance() < 100) {
			account.reduceBalance(25);
		}

		account.passTimeAndCalcAPR(1);
	}

	private void removeZeroBalanceAccounts(ArrayList<String> accountsToRemove) {
		for (String quickId : accountsToRemove) {
			accounts.remove(quickId);
		}
	}

}
