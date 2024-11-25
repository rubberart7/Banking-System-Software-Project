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
		if (commandParts.size() < 2) {
			return false;
		}
		String commandType = commandParts.get(0);
		if (commandType.equalsIgnoreCase("create")) {
			CreateCommandValidator createValidator = new CreateCommandValidator(bank);
			return createValidator.validate(commandParts);
		}

		else if (commandType.equalsIgnoreCase("deposit")) {
			DepositCommandValidator depositValidator = new DepositCommandValidator(bank);
			return depositValidator.validate(commandParts);
		}

		else if (commandType.equalsIgnoreCase("withdraw")) {
			WithdrawCommandValidator withdrawValidator = new WithdrawCommandValidator(bank);
			return withdrawValidator.validate(commandParts);
		}

		else if (commandType.equalsIgnoreCase("pass")) {
			PassTimeCommandValidator passTimeValidator = new PassTimeCommandValidator(bank);
			return passTimeValidator.validate(commandParts);
		}

		return false;
	}

}
