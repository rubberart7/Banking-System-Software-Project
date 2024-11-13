import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

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

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void create_checking_acc() {
		String command = "create checking 12345678 0.1";
		commandProcessor.processCommand(command);

		assertAccountCreated(command);

	}

	@Test
	void create_saving_acc() {
		String command = "create savings 12345678 0.1";
		commandProcessor.processCommand(command);

		assertAccountCreated(command);

	}

	@Test
	void create_an_account_twice() {
		String commandOne = "create savings 12345678 0.1";
		String commandTwo = "create checking 87654321 0.1";
		commandProcessor.processCommand(commandOne);
		commandProcessor.processCommand(commandTwo);

		assertAccountCreated(commandOne);
		assertAccountCreated(commandTwo);

	}

	@Test
	void create_cd_account() {
		String command = "create cd 12345678 3.5 1000";
		commandProcessor.processCommand(command);

		assertAccountCreated(command);
	}

	@Test
	void create_cd_account_twice() {
		String commandOne = "create cd 12345678 3.5 1000";
		String commandTwo = "create cd 87654321 3.5 1000";
		commandProcessor.processCommand(commandOne);
		commandProcessor.processCommand(commandTwo);

		assertAccountCreated(commandOne);
		assertAccountCreated(commandTwo);
	}

}
