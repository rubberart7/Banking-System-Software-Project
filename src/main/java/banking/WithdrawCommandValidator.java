package banking;

import java.util.ArrayList;

public class WithdrawCommandValidator extends CommandValidator {
	protected WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	protected boolean validate(ArrayList<String> commandParts) {
		if (commandParts.size() != 3) {
			return false;
		}
		String idValue = commandParts.get(1);
		String amount = commandParts.get(2);

		double withdrawAmount = parseAmount(amount);

		return isValidId(idValue) && isValidAmount(idValue, withdrawAmount);
	}

	private boolean isValidAmount(String idValue, double withdrawalAmount) {
		String accountType = bank.getAccounts().get(idValue).getAccountType();
		switch (accountType) {
		case "checking":
			return withdrawalAmount <= 400 && withdrawalAmount >= 0;

		case "savings":
			return withdrawalAmount <= 1000 && withdrawalAmount >= 0 && onlyOneWithdrawal(idValue);
		case "cd":
			return isValidToWithdraw(idValue, withdrawalAmount);
		default:
			return false;

		}

	}

	private boolean onlyOneWithdrawal(String idValue) {
		SavingsAccount account = (SavingsAccount) bank.getAccounts().get(idValue);
		if (account.getMonthlyWithdrawals() < 1) {
			return true;
		}

		return false;
	}

	private boolean isValidToWithdraw(String idValue, double withdrawalAmount) {
		Account account = bank.getAccounts().get(idValue);
		double epsilon = 0.01;
//		System.out.println(
//				"The account balance is " + account.getBalance() + " and the withdrawal amount is " + withdrawalAmount);
		if (account.getAge() >= 12 && (Math.abs(withdrawalAmount - account.getBalance()) <= epsilon
				|| withdrawalAmount > account.getBalance())) {

			return true;
		}
		return false;

	}

	private boolean isValidId(String idValue) {
		return idValue.length() == 8 && idValue.matches("\\d{8}");
	}

	private double parseAmount(String amount) {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
