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
		String depositedAmount = commandParts.get(2);

		return isValidId(idValue) && accountExists(idValue) && canParseAmount(depositedAmount)
				&& isValidAmountToDeposit(idValue, parseAmount(depositedAmount));
	}

	private boolean isValidAmountToDeposit(String idValue, double depositedAmount) {
		String accountType = bank.getAccounts().get(idValue).getAccountType();

		switch (accountType) {
		case "checking":
			return depositedAmount <= 1000 && depositedAmount >= 0;
		case "savings":
			return depositedAmount <= 2500 && depositedAmount >= 0;
		default:
			return false;
		}
	}
}
