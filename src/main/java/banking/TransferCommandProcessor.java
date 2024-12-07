package banking;

import java.util.ArrayList;

public class TransferCommandProcessor extends CommandProcessor {
	protected TransferCommandProcessor(Bank bank) {
		super(bank);
	}

	protected void process(ArrayList<String> commandParts) {
		String accOneId = commandParts.get(1);
		String accTwoId = commandParts.get(2);

		Double amount = Double.parseDouble(commandParts.get(3));

		bank.getAccounts().get(accOneId).withdraw(amount);
		bank.getAccounts().get(accTwoId).deposit(amount);
	}
}
