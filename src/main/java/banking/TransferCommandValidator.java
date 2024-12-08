package banking;

import java.util.ArrayList;

public class TransferCommandValidator extends CommandValidator {
	protected TransferCommandValidator(Bank bank) {
		super(bank);
	}

	protected boolean validate(ArrayList<String> commandParts) {
		if (commandParts.size() != 4) {
			return false;
		}
		String idValueOne = commandParts.get(1);
		String idValueTwo = commandParts.get(2);
		String transferAmount = commandParts.get(3);

		return isValidId(idValueOne) && isValidId(idValueTwo) && notSameID(idValueOne, idValueTwo)
				&& accountExists(idValueOne) && accountExists(idValueTwo) && notCDAccount(idValueOne)
				&& notCDAccount(idValueTwo) && canParseAmount(transferAmount)
				&& isValidAmountToWithdraw(idValueOne, parseAmount(transferAmount));
	}

	private boolean notSameID(String idValueOne, String idValueTwo) {
		return (!(idValueOne.equals(idValueTwo)));
	}

	private boolean notCDAccount(String idValue) {
		String accountType = bank.getAccounts().get(idValue).getAccountType();
		if (accountType.equalsIgnoreCase("cd")) {
			return false;
		}
		return true;
	}

}
