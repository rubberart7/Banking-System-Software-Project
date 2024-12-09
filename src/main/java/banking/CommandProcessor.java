package banking;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandProcessor {
	protected Bank bank;

	protected CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	protected void process(String command) {
		ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(" ")));
		String commandType = commandParts.get(0);
		if (commandType.equalsIgnoreCase("create")) {
			CreateCommandProcessor createProcessor = new CreateCommandProcessor(bank);
			createProcessor.process(commandParts);
		} else if (commandType.equalsIgnoreCase("deposit")) {
			DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
			depositCommandProcessor.process(commandParts);
		} else if (commandType.equalsIgnoreCase("withdraw")) {
			WithdrawCommandProcessor withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
			withdrawCommandProcessor.process(commandParts);
		} else if (commandType.equalsIgnoreCase("pass")) {
			PassTimeCommandProcessor passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
			passTimeCommandProcessor.process(commandParts);
		} else if (commandType.equalsIgnoreCase("transfer")) {
			TransferCommandProcessor transferProcessor = new TransferCommandProcessor(bank);
			transferProcessor.process(commandParts);
		}
	}

}
