import java.util.ArrayList;
import java.util.Arrays;

public class CreateCommandValidator {
	private static final ArrayList<String> VALID_ACCOUNT_TYPES = new ArrayList<>(
			Arrays.asList("checking", "savings", "cd"));
	private Bank bank;

	public CreateCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(" ")));

		String accType = commandParts.get(1).toLowerCase();
		String idValue = commandParts.get(2);

		if (idValue.length() != 8 || !idValue.matches("\\d{8}")) {
			return false;
		}
		if (!VALID_ACCOUNT_TYPES.contains(accType)) {
			return false;
		}

		if ((accType.equals("checking") || accType.equals("savings")) && (commandParts.size() != 4)) {
			return false;
		}

		if (accType.equals("cd") && commandParts.size() != 5) {
			return false;
		}

		return true;
	}
}
