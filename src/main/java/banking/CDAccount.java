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

}
