import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String QUICK_ID = "12345678";
	public static final String SECOND_ID = "87654321";
	public static final String THIRD_ID = "24681357";
	public static final double QUICK_APR = 7.0;
	public static final double QUICK_INITIAL_BALANCE = 200;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	public void addCommonAccounts() {
		bank.addRegularAccount(QUICK_ID, QUICK_APR, "CheckingAccount");
		bank.addCDAccount(SECOND_ID, QUICK_APR, QUICK_INITIAL_BALANCE);
		bank.addRegularAccount(THIRD_ID, QUICK_APR, "SavingsAccount");
	}

	@Test
	public void bank_has_no_accounts_initially() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void add_one_account_to_bank() {
		bank.addCDAccount(QUICK_ID, QUICK_APR, QUICK_INITIAL_BALANCE);
		int actual = bank.getSize();

		assertEquals(1, actual);
	}

	@Test
	public void add_two_accounts_to_bank() {
		bank.addRegularAccount(QUICK_ID, QUICK_APR, "CheckingAccount");
		bank.addCDAccount(SECOND_ID, QUICK_APR, QUICK_INITIAL_BALANCE);

		int actual = bank.getSize();

		assertEquals(2, actual);
	}

	@Test
	public void retrieve_the_correct_account_from_the_bank() {
		addCommonAccounts();

		Account account = bank.getAccounts().get(SECOND_ID);
		String actual = account.getIdValue();

		assertEquals(SECOND_ID, actual);

	}

	@Test
	public void deposit_money_into_the_correct_account() {
		addCommonAccounts();

		Account account = bank.getAccounts().get(QUICK_ID);
		account.deposit(100.0);
		double actual = account.getBalance();

		assertEquals(100.0, actual);

	}

	@Test
	public void deposit_money_into_the_correct_account_twice() {
		addCommonAccounts();

		Account account = bank.getAccounts().get(THIRD_ID);
		account.deposit(100.0);
		account.deposit(100.0);

		double actual = account.getBalance();

		assertEquals(200.0, actual);

	}

	@Test
	public void withdraw_money_from_the_correct_account() {
		addCommonAccounts();

		Account account = bank.getAccounts().get(SECOND_ID);
		account.withdraw(50.0);
		double actual = account.getBalance();

		assertEquals(150.0, actual);
	}

	@Test
	public void withdraw_money_from_the_correct_account_twice() {
		addCommonAccounts();

		Account account = bank.getAccounts().get(SECOND_ID);
		account.withdraw(50.0);
		account.withdraw(50.0);
		double actual = account.getBalance();

		assertEquals(100, actual);
	}

}
