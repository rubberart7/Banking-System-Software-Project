package banking;

import java.util.ArrayList;

public class PassTimeCommandProcessor extends CommandProcessor {

	protected PassTimeCommandProcessor(Bank bank) {
		super(bank);
	}

	protected void process(ArrayList<String> commandParts) {
		int months = Integer.parseInt(commandParts.get(1));
		bank.passTime(months);
	}
}
