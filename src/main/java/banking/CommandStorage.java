package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class CommandStorage {
	private final List<String> invalidCommands = new ArrayList<>();
	private final Map<String, List<String>> validCommands = new HashMap<>();

	protected Bank bank;

	protected CommandStorage(Bank bank) {
		this.bank = bank;
	}

	protected void addInvalidCommand(String command) {
		invalidCommands.add(command);

	}

	protected List<String> getInvalidCommands() {
		return invalidCommands;
	}

	protected void addValidCommand(String command) {
		ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(" ")));
		String commandType = commandParts.get(0);

		if (commandType.equalsIgnoreCase("withdraw") || commandType.equalsIgnoreCase("deposit")) {
			String idValue = commandParts.get(1);
			storeCommandForEachAccount(idValue, command);
		} else if (commandType.equalsIgnoreCase("transfer")) {
			String idValueOne = commandParts.get(1);
			String idValueTwo = commandParts.get(2);

			storeCommandForEachAccount(idValueOne, command);
			storeCommandForEachAccount(idValueTwo, command);
		}
	}

	private void storeCommandForEachAccount(String idValue, String command) {
		if (validCommands.get(idValue) != null) {
			validCommands.get(idValue).add(command);

		} else if (validCommands.get(idValue) == null) {
			validCommands.put(idValue, new ArrayList<>());
			validCommands.get(idValue).add(command);
		}
	}

	protected List<String> getOutput() {
		List<String> output = new ArrayList<>();
		for (String idValue : bank.getAccountsInOrder()) {
			output.add(getFormattedAccountStatus(idValue));
			if (validCommands.get(idValue) != null) {
				output.addAll(validCommands.get(idValue));
			}
		}
		output.addAll(invalidCommands);
		return output;
	}

	private String getFormattedAccountStatus(String idValue) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		Account account = bank.getAccounts().get(idValue);
		String accountType = account.getAccountType();
		String capitalizedAccType = accountType.substring(0, 1).toUpperCase() + accountType.substring(1);
		String formattedBalance = decimalFormat.format(account.getBalance());
		String formattedAPR = decimalFormat.format(account.getAprValue());
		return capitalizedAccType + " " + account.getIdValue() + " " + formattedBalance + " " + formattedAPR;

	}

	protected void removeCommandsForAccount(String idValue) {
		validCommands.remove(idValue);
	}
}
