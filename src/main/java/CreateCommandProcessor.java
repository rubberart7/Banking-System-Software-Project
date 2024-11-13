import java.util.ArrayList;

public class CreateCommandProcessor {
	private Bank bank;

	public CreateCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	protected void process(ArrayList<String> commandParts) {
		bank.addRegularAccount(commandParts.get(2), Double.parseDouble(commandParts.get(3)), commandParts.get(1));
	}
}
