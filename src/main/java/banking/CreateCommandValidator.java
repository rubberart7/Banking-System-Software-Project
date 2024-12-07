package banking;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateCommandValidator extends CommandValidator {
	private static final ArrayList<String> VALID_ACCOUNT_TYPES = new ArrayList<>(
			Arrays.asList("checking", "savings", "cd"));

	protected CreateCommandValidator(Bank bank) {
		super(bank);
	}

	protected boolean validate(ArrayList<String> commandParts) {
		if (commandParts.size() < 4) {
			return false;
		}

		String accType = commandParts.get(1).toLowerCase();
		String idValue = commandParts.get(2);
		String stringApr = commandParts.get(3);
		double aprValue = Double.parseDouble(stringApr);

		return isValidId(idValue) && isValidAccountType(accType) && isValidAccountDetails(accType, commandParts)
				&& isValidApr(aprValue) && !accountExists(idValue);
	}

	private boolean isValidAccountType(String accType) {
		return VALID_ACCOUNT_TYPES.contains(accType);
	}

	private boolean isValidAccountDetails(String accType, ArrayList<String> commandParts) {
		if (accType.equals("checking") || accType.equals("savings")) {
			return commandParts.size() == 4;
		}

		if (accType.equals("cd")) {
			return commandParts.size() == 5 && isValidCdBalance(commandParts.get(4));
		}

		return true;
	}

	private boolean isValidCdBalance(String minBalance) {
		double balance = Double.parseDouble(minBalance);
		return balance >= 1000 && balance <= 10000;
	}

	private boolean isValidApr(double aprValue) {
		return aprValue >= 0 && aprValue <= 10;
	}
}
