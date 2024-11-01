import java.util.ArrayList;

public class DepositCommandValidator {
	private Bank bank;

	public DepositCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(ArrayList<String> commandParts) {
		String idValue = commandParts.get(1);
		String amount = commandParts.get(2);
		double depositedAmount = Double.parseDouble(amount);

		if (depositedAmount < 0) {
			return false;
		}

		if (bank.getAccounts().get(idValue).getAccountType().equals("checking") && depositedAmount > 1000) {
			return false;
		}

		return true;
	}
}
