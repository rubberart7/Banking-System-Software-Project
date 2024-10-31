public class CommandValidator {
	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] commandParts = command.split(" ");

		String idValue = commandParts[2];
		if (idValue.length() != 8) {
			return false;
		} else {
			return true;
		}
	}
}
