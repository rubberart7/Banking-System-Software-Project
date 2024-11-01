import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandValidator {
	private Bank bank;
	private Map<String, Object> validators;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		this.validators = new HashMap<>();

		this.validators.put("create", new CreateCommandValidator(bank));
		this.validators.put("deposit", new DepositCommandValidator(bank));

	}

	protected boolean validate(String command) {
		ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(" ")));
		if (commandParts.size() < 3) {
			return false;
		}
		String commandType = commandParts.get(0).toLowerCase();
		if (commandType.equals("create")) {
			CreateCommandValidator createValidator = (CreateCommandValidator) validators.get("create");
			return createValidator.validate(commandParts);
		}

		else if (commandType.equals("deposit")) {
			DepositCommandValidator depositValidator = (DepositCommandValidator) validators.get("deposit");
			return depositValidator.validate(commandParts);
		}

		return false;
	}

}
