import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	private void assertAccountCreated(String command) {
		ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(" ")));
		String accType = commandParts.get(1);
		String idValue = commandParts.get(2);
		Double apr = Double.parseDouble(commandParts.get(3));

		assertTrue(bank.accountExistsById(idValue));
		if (accType.equals("checking") || accType.equals("savings")) {
			assertEquals(bank.getAccounts().get(idValue).getAccountType(), accType);
		}
		assertEquals(bank.getAccounts().get(idValue).getAprValue(), apr);
	}

//	create command tests
	@Test
	void create_checking_acc_has_acc_in_bank_with_the_correct_id_value() {
		commandProcessor.processCommand("create checking 12345678 0.1");

		assertTrue(bank.accountExistsById("12345678"));
	}

	@Test
	void create_checking_acc_has_acc_in_bank_with_the_correct_apr_value() {
		commandProcessor.processCommand("create checking 12345678 2.0");

		assertEquals(bank.getAccounts().get("12345678").getAprValue(), 2.0);
	}

	@Test
	void create_checking_acc_has_acc_in_bank_with_the_correct_account_type() {
		commandProcessor.processCommand("create checking 12345678 2.0");

		assertEquals(bank.getAccounts().get("12345678").getAccountType(), "checking");
	}

	@Test
	void created_checking_acc_has_starting_balance_of_zero() {
		commandProcessor.processCommand("create checking 12345678 2.0");

		assertEquals(0, bank.getAccounts().get("12345678").getBalance());

	}

	@Test
	void create_savings_acc_has_acc_in_bank_with_the_correct_id_value() {
		commandProcessor.processCommand("create savings 12345678 0.1");

		assertTrue(bank.accountExistsById("12345678"));
	}

	@Test
	void create_savings_acc_has_acc_in_bank_with_the_correct_apr_value() {
		commandProcessor.processCommand("create savings 12345678 0.1");

		assertEquals(bank.getAccounts().get("12345678").getAprValue(), 0.1);
	}

	@Test
	void create_savings_acc_has_acc_in_bank_with_the_correct_account_type() {
		commandProcessor.processCommand("create savings 12345678 2.0");

		assertEquals(bank.getAccounts().get("12345678").getAccountType(), "savings");
	}

	@Test
	void created_savings_acc_has_starting_balance_of_zero() {
		commandProcessor.processCommand("create savings 12345678 2.0");

		assertEquals(0, bank.getAccounts().get("12345678").getBalance());

	}

	@Test
	void create_cd_acc_has_acc_in_bank_with_the_correct_id_value() {
		String command = "create cd 12345678 0.1 1500";
		commandProcessor.processCommand(command);

		assertTrue(bank.accountExistsById("12345678"));
	}

	@Test
	void create_cd_acc_has_acc_in_bank_with_the_correct_apr_value() {
		commandProcessor.processCommand("create cd 12345678 0.1 1000");

		assertEquals(bank.getAccounts().get("12345678").getAprValue(), 0.1);
	}

	@Test
	void create_cd_acc_has_acc_in_bank_with_the_correct_account_type() {
		commandProcessor.processCommand("create cd 12345678 2.0 1500");

		assertEquals(bank.getAccounts().get("12345678").getAccountType(), "cd");
	}

	@Test
	void create_cd_has_starting_balance_of_provided_value() {
		commandProcessor.processCommand("create cd 12345678 2.0 1500");

		assertEquals(1500, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void can_create_checking_accounts_twice() {
		commandProcessor.processCommand("create checking 12345678 2.1");
		commandProcessor.processCommand("create checking 87654321 2.1");

		assertAccountCreated("create checking 12345678 2.1");
		assertAccountCreated("create checking 87654321 2.1");
	}

	@Test
	void can_create_savings_accounts_twice() {
		commandProcessor.processCommand("create savings 12345678 2.1");
		commandProcessor.processCommand("create savings 87654321 2.1");

		assertAccountCreated("create savings 12345678 2.1");
		assertAccountCreated("create savings 87654321 2.1");
	}

	@Test
	void can_create_cd_accounts_twice() {
		commandProcessor.processCommand("create cd 12345678 2.1 1500");
		commandProcessor.processCommand("create cd 87654321 2.1 1200");

		assertAccountCreated("create cd 12345678 2.1 1500");
		assertAccountCreated("create cd 87654321 2.1 1200");
	}

	@Test
	void can_create_a_variety_of_accounts() {
		commandProcessor.processCommand("create checking 12345678 2.1");
		commandProcessor.processCommand("create cd 87654321 2.1 1200");
		commandProcessor.processCommand("create savings 24681357 2.1");

		assertAccountCreated("create checking 12345678 2.1");
		assertAccountCreated("create cd 87654321 2.1 1200");
		assertAccountCreated("create savings 24681357 2.1");
	}

	@Test
	void bank_has_the_correct_number_of_accounts() {
		commandProcessor.processCommand("create checking 12345678 2.1");
		commandProcessor.processCommand("create cd 87654321 2.1 1200");
		commandProcessor.processCommand("create savings 24681357 2.1");

		assertEquals(3, bank.getAccounts().size());

	}

//	deposit command tests
	@Test
	void deposit_into_checking_acc_increases_balance_of_correct_acc() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		bank.addRegularAccount("87654321", 2.0, "checking");

		commandProcessor.processCommand("deposit 12345678 100");

		assertEquals(100, bank.getAccounts().get("12345678").getBalance());
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
