import java.util.ArrayList;
import java.util.Arrays;

public class CommandProcessor {
	private Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	protected void processCommand(String command) {
		ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(" ")));
		String commandType = commandParts.get(0);
		if (commandType.equals("create")) {
			CreateCommandProcessor createProcessor = new CreateCommandProcessor(bank);
			createProcessor.process(commandParts);
		}
	}
}
