package banking;

public class CheckingAccount extends Account {
	protected CheckingAccount(String idValue, double aprValue) {
		super(idValue, aprValue);
	}

	@Override
	protected String getAccountType() {
		return "checking".toLowerCase();
	}

	@Override
	protected void passTimeAndCalcAPR(int months) {
		time += months;
		double aprDec = ((aprValue / 100) / 12);
		double monthlyInterest = aprDec * balance;
		balance += monthlyInterest;
	}

}