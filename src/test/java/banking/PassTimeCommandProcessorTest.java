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
	void pass_time_for_checking_acc_with_balance_of_1000_and_apr_of_3_percent_calculates_correct_new_balance() {
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");
		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(1002.5, actualBalance);
	}

	@Test
	void pass_time_for_savings_acc_with_balance_of_1000_and_apr_of_3_percent_calculates_correct_new_balance() {
		commandProcessor.processCommand("create savings 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");
		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(1002.5, actualBalance);
	}

	@Test
	void pass_time_for_cd_acc_with_balance_of_2000_and_two_point_one_percent_apr_calculates_correct_new_balance() {
		commandProcessor.processCommand("create cd 12345678 2.1 2000");
		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(2014.0367928937578, actualBalance);

	}

}
