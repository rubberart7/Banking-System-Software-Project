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
		boolean isValid = true;
		if (commandParts.size() < 4) {
			return false;
		}

		String accType = commandParts.get(1).toLowerCase();
		String idValue = commandParts.get(2);
		String stringApr = commandParts.get(3);
		double aprValue = Double.parseDouble(stringApr);

		if (idValue.length() != 8 || !idValue.matches("\\d{8}")) {
			isValid = false;
		} else if (!VALID_ACCOUNT_TYPES.contains(accType)) {
			isValid = false;
		}

		else if ((accType.equals("checking") || accType.equals("savings")) && (commandParts.size() != 4)) {
			isValid = false;
		}

		else if (accType.equals("cd")) {
			if (commandParts.size() != 5) {
				return false;
			}
			String minBalance = commandParts.get(4);
			if (Double.parseDouble(minBalance) < 1000 || Double.parseDouble(minBalance) > 10000) {
				isValid = false;
			}
		}

		else if (aprValue < 0 || aprValue > 10) {
			isValid = false;
		} else if (bank.accountExistsById(idValue)) {
			isValid = false;
		}
		return isValid;
	}
}
