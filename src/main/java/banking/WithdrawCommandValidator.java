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

		return isValidId(idValue) && accountExists(idValue) && canParseAmount(amount)
				&& isValidAmountToWithdraw(idValue, withdrawAmount);
	}

}
