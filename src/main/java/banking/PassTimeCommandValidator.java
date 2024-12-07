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

		String months = commandParts.get(1);

		return canParseMonths(months) && isValidMonthValue(parseMonths(months));
	}

	private boolean canParseMonths(String months) {
		try {
			Integer.parseInt(months);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isValidMonthValue(int months) {
		if (months >= 1 && months <= 60) {
			return true;
		}
		return false;
	}

	private int parseMonths(String monthsAmount) {
		return Integer.parseInt(monthsAmount);
	}

}
