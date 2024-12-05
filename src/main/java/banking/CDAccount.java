package banking;

public class CDAccount extends Account {

	protected CDAccount(String idValue, double aprValue, double startingBalance) {
		super(idValue, aprValue);
		this.balance = startingBalance;
	}

	@Override
	protected String getAccountType() {
		return "cd".toLowerCase();
	}

	@Override
	protected void passTimeAndCalcAPR(int month) {
		time += month;
		double aprDec = ((aprValue / 100) / 12);
		for (int i = 0; i < 4; i++) {
			double monthlyInterest = aprDec * balance;
			balance += monthlyInterest;
		}

	}

}
