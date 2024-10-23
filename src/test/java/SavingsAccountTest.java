import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsAccountTest {
	SavingsAccount account;

	@BeforeEach
	public void setUp() {
		account = new SavingsAccount("87654321", 4.0);
	}

	@Test
	public void default_checking_account_starting_balance_is_zero() {
		double actual = account.getBalance();

		assertEquals(account.DEFAULT_STARTING_BALANCE, actual);
	}
}
