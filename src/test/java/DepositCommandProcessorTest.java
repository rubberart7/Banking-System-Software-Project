import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void deposit_into_checking_acc_increases_balance_of_correct_acc() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		bank.addRegularAccount("87654321", 2.0, "checking");

		commandProcessor.processCommand("deposit 12345678 100");

		assertEquals(100, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void can_deposit_zero_into_checking_acc() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		commandProcessor.processCommand("deposit 12345678 0");

		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void deposit_into_checking_acc_twice_increases_balance_of_correct_acc() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		bank.addRegularAccount("87654321", 2.0, "checking");

		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("deposit 12345678 200");

		assertEquals(300, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void deposit_into_savings_acc_increases_balance_of_correct_acc() {
		bank.addRegularAccount("12345678", 2.0, "savings");
		bank.addRegularAccount("87654321", 2.0, "savings");

		commandProcessor.processCommand("deposit 12345678 100");

		assertEquals(100, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void can_deposit_zero_into_savings_acc() {
		bank.addRegularAccount("12345678", 2.0, "savings");
		commandProcessor.processCommand("deposit 12345678 0");

		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void deposit_into_savings_acc_twice_increases_balance_of_correct_acc() {
		bank.addRegularAccount("12345678", 2.0, "savings");
		bank.addRegularAccount("87654321", 2.0, "savings");

		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("deposit 12345678 200");

		assertEquals(300, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void can_deposit_into_correct_account_even_with_many_different_types_of_accs() {
		bank.addRegularAccount("12345678", 2.0, "savings");
		bank.addRegularAccount("87654321", 2.0, "checking");
		bank.addRegularAccount("22222222", 2.0, "savings");

		commandProcessor.processCommand("deposit 22222222 300");

		assertEquals(300, bank.getAccounts().get("22222222").getBalance());
	}

}
