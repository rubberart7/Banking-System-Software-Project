package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	private final String INVALID_CREATE_COMMAND = "creat checking 12345678 0.5";
	private final String INVALID_DEPOSIT_COMMAND = "depositt 12345678 100";

	private final String INVALID_WITHDRAW_COMMAND = "withdraww 12345678 100";

	private final String INVALID_PASS_TIME_COMMAND = "pass 61";

	private final String INVALID_TRANSFER_COMMAND = "transfer 12345678 8765432 400";

	CommandStorage commandStorage;

	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	@Test
	void invalid_commands_list_is_empty_when_no_commands_are_added() {
		assertEquals(0, commandStorage.getInvalidCommands().size());
	}

	@Test
	void add_one_invalid_create_command_to_invalid_commands_list() {
		commandStorage.addInvalidCommand(INVALID_CREATE_COMMAND);
		assertEquals(INVALID_CREATE_COMMAND, commandStorage.getInvalidCommands().get(0));
	}

	@Test
	void add_two_different_invalid_commands_to_invalid_commands_list() {
		commandStorage.addInvalidCommand(INVALID_CREATE_COMMAND);
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);
		assertEquals(INVALID_CREATE_COMMAND, commandStorage.getInvalidCommands().get(0));
		assertEquals(INVALID_DEPOSIT_COMMAND, commandStorage.getInvalidCommands().get(1));

	}

	@Test
	void size_of_invalid_commands_is_one_when_adding_one_deposit_invalid_command() {
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);
		assertEquals(1, commandStorage.getInvalidCommands().size());

	}

	@Test
	void size_of_invalid_commands_is_two_when_adding_two_different_invalid_commands() {
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);
		commandStorage.addInvalidCommand(INVALID_CREATE_COMMAND);

		assertEquals(2, commandStorage.getInvalidCommands().size());

	}

	@Test
	void size_of_invalid_commands_is_two_when_adding_two_same_invalid_commands() {
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);

		assertEquals(2, commandStorage.getInvalidCommands().size());

	}

	@Test
	void size_of_invalid_commands_is_one_when_adding_invalid_pass_time_command() {
		commandStorage.addInvalidCommand(INVALID_PASS_TIME_COMMAND);

		assertEquals(1, commandStorage.getInvalidCommands().size());
	}

	@Test
	void size_of_invalid_commands_is_two_when_adding_two_invalid_pass_time_command() {
		commandStorage.addInvalidCommand(INVALID_PASS_TIME_COMMAND);

		assertEquals(1, commandStorage.getInvalidCommands().size());
	}

	@Test
	void size_of_invalid_commands_is_one_when_adding_invalid_transfer_command() {
		commandStorage.addInvalidCommand(INVALID_TRANSFER_COMMAND);

		assertEquals(1, commandStorage.getInvalidCommands().size());
	}

	@Test
	void size_of_invalid_commands_is_two_when_adding_two_invalid_transfer_commands() {
		commandStorage.addInvalidCommand(INVALID_TRANSFER_COMMAND);
		commandStorage.addInvalidCommand(INVALID_TRANSFER_COMMAND);

		assertEquals(2, commandStorage.getInvalidCommands().size());
	}

	@Test
	void adding_many_different_invalid_commands_adds_shows_the_invalid_commands_in_order() {
		commandStorage.addInvalidCommand(INVALID_CREATE_COMMAND);
		commandStorage.addInvalidCommand(INVALID_TRANSFER_COMMAND);
		commandStorage.addInvalidCommand(INVALID_PASS_TIME_COMMAND);
		commandStorage.addInvalidCommand(INVALID_WITHDRAW_COMMAND);
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);

		assertEquals(INVALID_CREATE_COMMAND, commandStorage.getInvalidCommands().get(0));
		assertEquals(INVALID_TRANSFER_COMMAND, commandStorage.getInvalidCommands().get(1));
		assertEquals(INVALID_PASS_TIME_COMMAND, commandStorage.getInvalidCommands().get(2));
		assertEquals(INVALID_WITHDRAW_COMMAND, commandStorage.getInvalidCommands().get(3));
		assertEquals(INVALID_DEPOSIT_COMMAND, commandStorage.getInvalidCommands().get(4));

	}

}
