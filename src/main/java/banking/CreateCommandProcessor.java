package banking;

import java.util.ArrayList;

public class CreateCommandProcessor extends CommandProcessor {

	protected CreateCommandProcessor(Bank bank) {
		super(bank);
	}

	protected void process(ArrayList<String> commandParts) {
		String accType = commandParts.get(1).toLowerCase();
		String idValue = commandParts.get(2);
		Double aprValue = Double.parseDouble(commandParts.get(3));

		if (accType.equals("cd")) {
			Double initialBalance = Double.parseDouble(commandParts.get(4));
			bank.addCDAccount(idValue, aprValue, initialBalance);
		} else if (accType.equals("checking") || commandParts.get(1).equals("savings")) {
			bank.addRegularAccount(idValue, aprValue, accType);

		}
	}
}
