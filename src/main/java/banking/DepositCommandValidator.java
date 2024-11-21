package banking;

import java.util.ArrayList;

public class DepositCommandValidator extends CommandValidator {

	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(ArrayList<String> commandParts) {
		String idValue = commandParts.get(1);
		String amount = commandParts.get(2);
		if (commandParts.size() != 3) {
			return false;
		}
		double depositedAmount;
		try {
			depositedAmount = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return false;
		}
		if (Double.parseDouble(amount) < 0) {
			return false;
		}

		if (idValue.length() != 8 || !idValue.matches("\\d{8}")) {
			return false;
		}

		String accountType = bank.getAccounts().get(idValue).getAccountType();

		if (accountType.equals("checking")) {
			if (depositedAmount > 1000) {
				return false;
			}
		} else if (accountType.equals("savings")) {
			if (depositedAmount > 2500) {
				return false;
			}
		} else if (accountType.equals("cd")) {
			return false;
		}

		return true;
	}
}
