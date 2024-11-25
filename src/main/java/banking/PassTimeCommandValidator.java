package banking;

import java.util.ArrayList;

public class PassTimeCommandValidator extends CommandValidator {
	protected PassTimeCommandValidator(Bank bank) {
		super(bank);
	}

	protected boolean validate(ArrayList<String> commandParts) {
		if (commandParts.size() != 2) {
			return false;
		}

		int months = parseMonths(commandParts.get(1));

		return isValidMonthValue(months);
	}

	private boolean isValidMonthValue(int months) {
		if (months >= 1 && months <= 60) {
			return true;
		}
		return false;
	}

	private int parseMonths(String monthsAmount) {
		try {
			return Integer.parseInt(monthsAmount);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

}
