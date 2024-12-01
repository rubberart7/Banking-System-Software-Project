package banking;

import java.util.ArrayList;

public class WithdrawCommandProcessor extends CommandProcessor {

	protected WithdrawCommandProcessor(Bank bank) {
		super(bank);
	}

	protected void process(ArrayList<String> commandParts) {
		bank.getAccounts().get(commandParts.get(1)).withdraw(Double.parseDouble(commandParts.get(2)));
	}
}
