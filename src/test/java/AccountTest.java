import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	Account account;

	@BeforeEach
	public void setUp() {
		account = new CheckingAccount("12345678", 9.0);
	}

	@Test
	public void default_checking_account_starting_balance_is_zero() {
		double actual = account.getBalance();

		assertEquals(account.DEFAULT_STARTING_BALANCE, actual);
	}

	@Test
	public void checking_account_created_with_id_value() {
		String actual = account.getIdValue();

		assertEquals("12345678", actual);
	}

	@Test
	public void checking_account_created_with_APR_value() {
		double actual = account.getAprValue();

		assertEquals(9.0, actual);
	}

	@Test
	public void deposit_money_increases_balance() {
		account.deposit(100.0);
		double actual = account.getBalance();

		assertEquals(100, actual);
	}

	@Test
	public void deposit_in_same_account_twice_increases_balance_properly() {
		account.deposit(100.0);
		account.deposit(100.0);

		double actual = account.getBalance();

		assertEquals(200, actual);
	}

	@Test
	public void withdraw_money_decreases_balance() {
		account.withdraw(50);
		double actual = account.getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void withdraw_from_same_account_twice_decreases_balance_properly() {
		account.withdraw(50.0);
		account.withdraw(50.0);

		double actual = account.getBalance();

		assertEquals(0, actual);
	}

}
