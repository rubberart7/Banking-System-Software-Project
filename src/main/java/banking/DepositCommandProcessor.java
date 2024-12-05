package banking;

import java.util.ArrayList;

public class DepositCommandProcessor extends CommandProcessor {

	protected DepositCommandProcessor(Bank bank) {
		super(bank);
	}

	protected void process(ArrayList<String> commandParts) {
		String idValue = commandParts.get(1);
		Double amount = Double.parseDouble(commandParts.get(2));

		bank.getAccounts().get(idValue).deposit(amount);
	}
}
