package banking;

import java.util.ArrayList;

public class CreateCommandProcessor extends CommandProcessor {

	protected CreateCommandProcessor(Bank bank) {
		super(bank);
	}

	protected void process(ArrayList<String> commandParts) {
		if (commandParts.get(1).equals("cd")) {
			bank.addCDAccount(commandParts.get(2), Double.parseDouble(commandParts.get(3)),
					Double.parseDouble(commandParts.get(4)));
		} else {
			bank.addRegularAccount(commandParts.get(2), Double.parseDouble(commandParts.get(3)), commandParts.get(1));

		}
	}
}
