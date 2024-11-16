package banking;

import java.util.ArrayList;

public class DepositCommandProcessor {

	private Bank bank;

	public DepositCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	protected void process(ArrayList<String> commandParts) {
		bank.getAccounts().get(commandParts.get(1)).deposit(Double.parseDouble(commandParts.get(2)));
	}
}
