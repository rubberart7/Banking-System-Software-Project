import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDAccountTest {
	CDAccount cdAccount;

	@BeforeEach
	public void setUp() {
		cdAccount = new CDAccount("87654321", 8.0, 200.0);
	}

	@Test
	public void default_cd_starting_balance_is_supplied_value() {
		double actual = cdAccount.getBalance();

		assertEquals(200, actual);

	}
}
