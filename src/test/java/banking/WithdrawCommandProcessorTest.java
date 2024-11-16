package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void withdraw_from_checking_acc_decreases_the_balance_of_the_correct_acc() {
		commandProcessor.processCommand("create checking 12345678 1.2");
		commandProcessor.processCommand("deposit 12345678 500");
		commandProcessor.processCommand("withdraw 12345678 100");

		assertEquals(400, bank.getAccounts().get("12345678").getBalance());
	}
}
