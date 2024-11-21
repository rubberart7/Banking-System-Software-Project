package banking;

import java.util.ArrayList;

public class DepositCommandProcessor extends CommandProcessor {

	public DepositCommandProcessor(Bank bank) {
		super(bank);
	}

	protected void process(ArrayList<String> commandParts) {
		bank.getAccounts().get(commandParts.get(1)).deposit(Double.parseDouble(commandParts.get(2)));
	}
}
