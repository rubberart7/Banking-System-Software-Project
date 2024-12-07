package banking;

import static org.junit.jupiter.api.Assertions.*;

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

	@Test
	void command_processor_can_process_create_command() {
		commandProcessor.process("create checking 12345678 2.1");
		assertAccountCreated("create checking 12345678 2.1");
	}

	@Test
	void command_processor_can_process_create_command_twice() {
		commandProcessor.process("create checking 12345678 2.1");
		commandProcessor.process("create checking 87654321 2.1");

		assertAccountCreated("create checking 12345678 2.1");
		assertAccountCreated("create checking 87654321 2.1");
	}

	@Test
	void command_processor_can_process_deposit_command() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		bank.addRegularAccount("87654321", 2.0, "checking");

		commandProcessor.process("deposit 12345678 100");

		assertEquals(100, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void command_processor_can_process_deposit_command_twice() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		bank.addRegularAccount("87654321", 2.0, "checking");

		commandProcessor.process("deposit 12345678 100");
		commandProcessor.process("deposit 87654321 200");

		assertEquals(100, bank.getAccounts().get("12345678").getBalance());
		assertEquals(200, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void command_processor_can_process_withdraw_command() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		bank.getAccounts().get("12345678").deposit(500);

		commandProcessor.process("withdraw 12345678 100");
		assertEquals(400, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void command_processor_can_process_withdraw_command_twice() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		bank.addRegularAccount("87654321", 2.0, "checking");

		bank.getAccounts().get("12345678").deposit(500);
		bank.getAccounts().get("87654321").deposit(500);

		commandProcessor.process("withdraw 12345678 100");
		commandProcessor.process("withdraw 87654321 200");

		assertEquals(400, bank.getAccounts().get("12345678").getBalance());
		assertEquals(300, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void command_processor_can_process_pass_time_command() {
		commandProcessor.process("create checking 12345678 10.0");
		commandProcessor.process("deposit 12345678 99");

		commandProcessor.process("pass 6");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

	@Test
	void command_processor_can_process_pass_time_command_twice() {
		commandProcessor.process("create checking 12345678 10.0");
		commandProcessor.process("deposit 12345678 99");

		commandProcessor.process("pass 3");
		commandProcessor.process("pass 3");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}
}
