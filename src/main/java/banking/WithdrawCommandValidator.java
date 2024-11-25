package banking;

import java.util.ArrayList;

public class WithdrawCommandValidator extends CommandValidator {
	protected WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	protected boolean validate(ArrayList<String> commandParts) {
		if (commandParts.size() != 3) {
			return false;
		}
		String idValue = commandParts.get(1);
		String amount = commandParts.get(2);

		double withdrawAmount = parseAmount(amount);

		return isValidId(idValue) && isValidAmount(idValue, withdrawAmount);
	}

	private boolean isValidAmount(String idValue, double depositedAmount) {
		String accountType = bank.getAccounts().get(idValue).getAccountType();
		switch (accountType) {
		case "checking":
			return depositedAmount <= 400 && depositedAmount >= 0;

		case "savings":
			return depositedAmount <= 1000 && depositedAmount >= 0;
		default:
			return false;

		}

	}

	private boolean isValidId(String idValue) {
		return idValue.length() == 8 && idValue.matches("\\d{8}");
	}

	private double parseAmount(String amount) {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
