package banking;

import java.util.ArrayList;

public class DepositCommandValidator extends CommandValidator {

	protected DepositCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(ArrayList<String> commandParts) {
		if (commandParts.size() != 3) {
			return false;
		}

		String idValue = commandParts.get(1);
		String amount = commandParts.get(2);

		double depositedAmount = parseAmount(amount);
		if (depositedAmount < 0) {
			return false;
		}

		return isValidId(idValue) && isValidAccount(idValue, depositedAmount);
	}

	private double parseAmount(String amount) {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private boolean isValidId(String idValue) {
		return idValue.length() == 8 && idValue.matches("\\d{8}");
	}

	private boolean isValidAccount(String idValue, double depositedAmount) {
		String accountType = bank.getAccounts().get(idValue).getAccountType();

		switch (accountType) {
		case "checking":
			return depositedAmount <= 1000;
		case "savings":
			return depositedAmount <= 2500;
		case "cd":
			return false;
		default:
			return false;
		}
	}
}
