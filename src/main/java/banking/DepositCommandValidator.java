package banking;

import java.util.ArrayList;

public class DepositCommandValidator extends CommandValidator {

	protected DepositCommandValidator(Bank bank) {
		super(bank);
	}

	protected boolean validate(ArrayList<String> commandParts) {
		if (commandParts.size() != 3) {
			return false;
		}

		String idValue = commandParts.get(1);

		double depositedAmount = parseAmount(commandParts.get(2));
		if (depositedAmount < 0) {
			return false;
		}

		return isValidId(idValue) && isValidAmount(idValue, depositedAmount);
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

	private boolean isValidAmount(String idValue, double depositedAmount) {
		String accountType = bank.getAccounts().get(idValue).getAccountType();

		switch (accountType) {
		case "checking":
			return depositedAmount <= 1000 && depositedAmount >= 0;
		case "savings":
			return depositedAmount <= 2500 && depositedAmount >= 0;
		case "cd":
			return false;
		default:
			return false;
		}
	}
}
