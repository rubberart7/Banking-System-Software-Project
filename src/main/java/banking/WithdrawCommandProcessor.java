package banking;

import java.util.ArrayList;

public class WithdrawCommandProcessor {
	protected Bank bank;

	protected WithdrawCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	protected void process(ArrayList<String> commandParts) {
		bank.getAccounts().get(commandParts.get(1)).withdraw(Double.parseDouble(commandParts.get(2)));
	}
}
