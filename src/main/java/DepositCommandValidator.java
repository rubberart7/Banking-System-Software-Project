import java.util.ArrayList;

public class DepositCommandValidator {
	private Bank bank;

	public DepositCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(ArrayList<String> commandParts) {
		String idValue = commandParts.get(1);
		String amount = commandParts.get(2);

		double depositedAmount;
		try {
			depositedAmount = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return false;
		}
		if (depositedAmount < 0) {
			return false;
		}

		if (idValue.length() != 8 || !idValue.matches("\\d{8}")) {
			return false;
		}

		String accountType = bank.getAccounts().get(idValue).getAccountType();

		if (accountType.equals("savings") || accountType.equals("checking")) {
			if (commandParts.size() != 3) {
				return false;
			}

			if (accountType.equals("savings") && depositedAmount > 2500) {
				return false;
			}

			if (accountType.equals("checking") && depositedAmount > 1000) {
				return false;
			}

			return true;
		}

		if (accountType.equals("cd")) {
			return false;
		}

		return true;
	}
}
