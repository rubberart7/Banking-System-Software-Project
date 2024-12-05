package banking;

public abstract class Account {
	public static final double DEFAULT_STARTING_BALANCE = 0;
	protected final String idValue;
	protected double balance;
	protected double aprValue;
	protected int time;
	protected int monthlyWithdrawals;

	protected Account(String idValue, double aprValue) {
		this.balance = DEFAULT_STARTING_BALANCE;
		this.aprValue = aprValue;
		this.idValue = idValue;
	}

	protected double getAprValue() {
		return aprValue;
	}

	protected String getIdValue() {
		return idValue;
	}

	protected void deposit(double amount) {
		balance += amount;
	}

	protected void withdraw(double amount) {
		if (amount >= 0 && balance >= amount) {
			balance -= amount;
		} else {
			balance = 0;
		}

	}

	protected double getBalance() {
		return balance;
	}

	protected void reduceBalance(double amount) {
		if (amount >= 0 && balance >= amount) {
			balance -= amount;
		} else {
			balance = 0;
		}

	}

	protected void passTimeAndCalcAPR(int month) {
		time += month;
		double aprDec = ((aprValue / 100) / 12);
		double monthlyInterest = aprDec * balance;
		balance += monthlyInterest;
	}

	protected abstract String getAccountType();

	protected int getAge() {
		return time;
	}

	protected void addMonthlyWithdrawal(int withdrawalNum) {
		monthlyWithdrawals += withdrawalNum;
	}

	public void addTime(int months) {
		time += months;
		monthlyWithdrawals = 0;
	}
}
