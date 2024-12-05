package banking;

public class CheckingAccount extends Account {

	protected CheckingAccount(String idValue, double aprValue) {
		super(idValue, aprValue);
	}

	@Override
	protected String getAccountType() {
		return "checking".toLowerCase();
	}

}