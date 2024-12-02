package banking;

public class SavingsAccount extends Account {
	protected SavingsAccount(String idValue, double aprValue) {
		super(idValue, aprValue);
	}

	@Override
	protected String getAccountType() {
		return "savings".toLowerCase();
	}

	@Override
	protected void passTimeAndCalcAPR(int months) {
		time += months;
		double aprDec = ((aprValue / 100) / 12);
		double monthlyInterest = aprDec * balance;
		balance += monthlyInterest;
	}

}
