import java.util.ArrayList;

public class WithdrawCommandProcessor {
	private Bank bank;

	public WithdrawCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	protected void process(ArrayList<String> commandParts) {
		bank.getAccounts().get(commandParts.get(1)).withdraw(Double.parseDouble(commandParts.get(2)));
	}
}
