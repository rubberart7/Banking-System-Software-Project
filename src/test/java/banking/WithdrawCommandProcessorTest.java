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

	@Test
	void withdraw_from_checking_acc_twice_decreases_the_balance_of_the_correct_acc() {
		commandProcessor.processCommand("create checking 12345678 1.2");
		commandProcessor.processCommand("deposit 12345678 500");
		commandProcessor.processCommand("withdraw 12345678 100");
		commandProcessor.processCommand("withdraw 12345678 100");

		assertEquals(300, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_zero_from_checking_acc_makes_sure_the_balance_is_the_same() {
		commandProcessor.processCommand("create checking 12345678 1.2");
		commandProcessor.processCommand("deposit 12345678 500");
		commandProcessor.processCommand("withdraw 12345678 0");

		assertEquals(500, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_more_than_balance_from_checking_makes_balance_0() {
		commandProcessor.processCommand("create checking 12345678 1.2");
		commandProcessor.processCommand("deposit 12345678 500");
		commandProcessor.processCommand("withdraw 12345678 600");
		assertEquals(0, bank.getAccounts().get("12345678").getBalance());

	}

	@Test
	void withdraw_from_savings_acc_decreases_the_balance_of_the_correct_acc() {
		commandProcessor.processCommand("create savings 12345678 1.2");
		commandProcessor.processCommand("deposit 12345678 500");
		commandProcessor.processCommand("withdraw 12345678 100");

		assertEquals(400, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_from_savings_acc_twice_decreases_the_balance_of_the_correct_acc() {
		commandProcessor.processCommand("create savings 12345678 1.2");
		commandProcessor.processCommand("deposit 12345678 500");
		commandProcessor.processCommand("withdraw 12345678 100");
		commandProcessor.processCommand("withdraw 12345678 100");

		assertEquals(300, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_zero_from_savings_acc_makes_sure_the_balance_is_the_same() {
		commandProcessor.processCommand("create savings 12345678 1.2");
		commandProcessor.processCommand("deposit 12345678 500");
		commandProcessor.processCommand("withdraw 12345678 0");

		assertEquals(500, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_more_than_balance_from_savings_makes_balance_0() {
		commandProcessor.processCommand("create savings 12345678 1.2");
		commandProcessor.processCommand("deposit 12345678 500");
		commandProcessor.processCommand("withdraw 12345678 600");
		assertEquals(0, bank.getAccounts().get("12345678").getBalance());

	}

	@Test
	void can_withdraw_from_correct_account_even_with_many_different_types_of_accs() {
		commandProcessor.processCommand("create savings 12345678 1.2");
		commandProcessor.processCommand("create checking 87654321 1.2");
		commandProcessor.processCommand("create savings 13572468 1.2");

		commandProcessor.processCommand("deposit 12345678 500");

		commandProcessor.processCommand("withdraw 12345678 200");

		assertEquals(300, bank.getAccounts().get("12345678").getBalance());
	}

}
