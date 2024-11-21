package banking;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandValidator {
	protected Bank bank;

	protected CommandValidator(Bank bank) {
		this.bank = bank;

	}

	protected boolean validate(String command) {
		ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(" ")));
		if (commandParts.size() < 3) {
			return false;
		}
		String commandType = commandParts.get(0).toLowerCase();
		if (commandType.equals("create")) {
			CreateCommandValidator createValidator = new CreateCommandValidator(bank);
			return createValidator.validate(commandParts);
		}

		else if (commandType.equals("deposit")) {
			DepositCommandValidator depositValidator = new DepositCommandValidator(bank);
			return depositValidator.validate(commandParts);
		}

		return false;
	}

}
