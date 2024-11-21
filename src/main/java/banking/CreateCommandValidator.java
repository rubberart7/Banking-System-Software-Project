package banking;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateCommandValidator extends CommandValidator {
	private static final ArrayList<String> VALID_ACCOUNT_TYPES = new ArrayList<>(
			Arrays.asList("checking", "savings", "cd"));

	public CreateCommandValidator(Bank bank) {
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

		if (idValue.length() != 8 || !idValue.matches("\\d{8}")) {
			return false;
		}
		if (!VALID_ACCOUNT_TYPES.contains(accType)) {
			return false;
		}

		if ((accType.equals("checking") || accType.equals("savings")) && (commandParts.size() != 4)) {
			return false;
		}

		if (accType.equals("cd")) {
			if (commandParts.size() != 5) {
				return false;
			}
			String minBalance = commandParts.get(4);
			double minimumBalance = Double.parseDouble(minBalance);
			if (minimumBalance < 1000 || minimumBalance > 10000) {
				return false;
			}
		}

		if (aprValue < 0 || aprValue > 10) {
			return false;
		}
		if (bank.accountExistsById(idValue)) {
			return false;
		}
		return true;
	}
}
