package banking;

public class SavingsAccount extends Account {
	protected SavingsAccount(String idValue, double aprValue) {
		super(idValue, aprValue);
	}

	@Override
	protected String getAccountType() {
		return "savings".toLowerCase();
	}

	protected int getMonthlyWithdrawals() {
		return monthlyWithdrawals;
	}

}
