public class SavingsAccount extends Account {
	public SavingsAccount(String idValue, double aprValue) {
		super(idValue, aprValue);
	}

	@Override
	protected String getAccountType() {
		return "savings".toLowerCase();
	}
}
