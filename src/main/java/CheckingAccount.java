public class CheckingAccount extends Account {
	public CheckingAccount(String idValue, double aprValue) {
		super(idValue, aprValue);
	}

	@Override
	protected String getAccountType() {
		return "checking".toLowerCase();
	}

}