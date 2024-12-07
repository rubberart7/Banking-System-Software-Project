package banking;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandValidator {
	protected Bank bank;

	protected CommandValidator(Bank bank) {
		this.bank = bank;

	}

	protected boolean validate(String command) {
		ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(" ")));
		if (commandParts.size() < 2) {
			return false;
		}
		String commandType = commandParts.get(0);
		if (commandType.equalsIgnoreCase("create")) {
			CreateCommandValidator createValidator = new CreateCommandValidator(bank);
			return createValidator.validate(commandParts);
		} else if (commandType.equalsIgnoreCase("deposit")) {
			DepositCommandValidator depositValidator = new DepositCommandValidator(bank);
			return depositValidator.validate(commandParts);
		} else if (commandType.equalsIgnoreCase("withdraw")) {
			WithdrawCommandValidator withdrawValidator = new WithdrawCommandValidator(bank);
			return withdrawValidator.validate(commandParts);
		} else if (commandType.equalsIgnoreCase("pass")) {
			PassTimeCommandValidator passTimeValidator = new PassTimeCommandValidator(bank);
			return passTimeValidator.validate(commandParts);
		} else if (commandType.equalsIgnoreCase("transfer")) {
			TransferCommandValidator transferValidator = new TransferCommandValidator(bank);
			return transferValidator.validate(commandParts);
		}

		return false;
	}

	protected boolean isValidAmountToWithdraw(String idValue, double withdrawalAmount) {
		String accountType = bank.getAccounts().get(idValue).getAccountType();
		switch (accountType) {
		case "checking":
			return withdrawalAmount <= 400 && withdrawalAmount >= 0;

		case "savings":
			return withdrawalAmount <= 1000 && withdrawalAmount >= 0 && onlyOneWithdrawal(idValue);
		case "cd":
			return canWithdrawFromCD(idValue, withdrawalAmount);
		default:
			return false;

		}

	}

	protected boolean accountExists(String idValue) {
		return bank.accountExistsById(idValue);
	}

	protected boolean onlyOneWithdrawal(String idValue) {
		SavingsAccount account = (SavingsAccount) bank.getAccounts().get(idValue);
		if (account.getMonthlyWithdrawals() < 1) {
			return true;
		}

		return false;
	}

	protected boolean canWithdrawFromCD(String idValue, double amount) {
		Account account = bank.getAccounts().get(idValue);
		if (account.getAge() >= 12 && (amount >= account.getBalance())) {

			return true;
		}
		return false;

	}

	protected boolean isValidId(String idValue) {
		return idValue.length() == 8 && idValue.matches("\\d{8}");
	}

	protected boolean canParseAmount(String amount) {
		try {
			Double.parseDouble(amount);
			return true;
		} catch (NumberFormatException e) {

			return false;
		}
	}

	protected double parseAmount(String amount) {
		return Double.parseDouble(amount);
	}

}
