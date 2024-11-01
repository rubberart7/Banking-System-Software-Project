public class CDAccount extends Account {

	public CDAccount(String idValue, double aprValue, double startingBalance) {
		super(idValue, aprValue);
		this.balance = startingBalance;
	}

	@Override
	protected String getAccountType() {
		return "cd".toLowerCase();
	}

}
