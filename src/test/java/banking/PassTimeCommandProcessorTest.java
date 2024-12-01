package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void remove_checking_acc_if_balance_is_0_from_the_start_and_one_month_passes() {
		bank.addRegularAccount("12345678", 2.5, "checking");
		commandProcessor.processCommand("pass 1");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

	@Test
	void remove_savings_acc_if_balance_is_0_from_the_start_and_one_month_passes() {
		bank.addRegularAccount("12345678", 2.5, "savings");
		commandProcessor.processCommand("pass 1");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

	@Test
	void passing_one_month_removes_25_dollars_from_checking_if_acc_balance_is_below_100() {
		commandProcessor.processCommand("create checking 12345678 7.5");
		commandProcessor.processCommand("deposit 12345678 99");

		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(74, actualBalance);

	}

	@Test
	void passing_two_months_removes_50_dollars_from_checking_if_acc_balance_is_below_100() {
		commandProcessor.processCommand("create checking 12345678 7.5");
		commandProcessor.processCommand("deposit 12345678 99");

		commandProcessor.processCommand("pass 2");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(49, actualBalance);

	}

	@Test
	void passing_one_month_removes_25_dollars_from_savings_if_acc_balance_is_below_100() {
		commandProcessor.processCommand("create savings 12345678 7.5");
		commandProcessor.processCommand("deposit 12345678 90");

		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(65, actualBalance);

	}

	@Test
	void passing_two_months_removes_50_dollars_from_savings_if_acc_balance_is_below_100() {
		commandProcessor.processCommand("create savings 12345678 7.5");
		commandProcessor.processCommand("deposit 12345678 99");

		commandProcessor.processCommand("pass 2");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(49, actualBalance);

	}

}
