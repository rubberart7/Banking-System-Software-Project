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

	public double getAprValue() {
		return aprValue;
	}

	public String getIdValue() {
		return idValue;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if (amount > 0 && balance >= amount) {
			balance -= amount;
		} else {
			balance = 0;
		}
	}

	public double getBalance() {
		return balance;
	}

}
