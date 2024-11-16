package banking;

public abstract class Account {
	public static final double DEFAULT_STARTING_BALANCE = 0;
	private final String idValue;
	protected double balance;
	private double aprValue;

	public Account(String idValue, double aprValue) {
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
		if (amount > 0 && balance >= amount) {
			balance -= amount;
		} else {
			balance = 0;
		}
	}

	protected double getBalance() {
		return balance;
	}

	protected abstract String getAccountType();

}
