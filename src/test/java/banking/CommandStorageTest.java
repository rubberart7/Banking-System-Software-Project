package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	private final String INVALID_CREATE_COMMAND = "creat checking 12345678 0.5";
	private final String INVALID_DEPOSIT_COMMAND = "depositt checking 12345678";
	CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	void invalid_commands_list_is_empty_when_no_commands_are_added() {
		assertEquals(0, commandStorage.getInvalidCommands().size());
	}

	@Test
	void add_one_invalid_command_to_invalid_commands_list() {
		commandStorage.addInvalidCommand(INVALID_CREATE_COMMAND);
		assertEquals(INVALID_CREATE_COMMAND, commandStorage.getInvalidCommands().get(0));
	}

	@Test
	void add_two_invalid_commands_to_invalid_commands_list() {
		commandStorage.addInvalidCommand(INVALID_CREATE_COMMAND);
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);
		assertEquals(INVALID_CREATE_COMMAND, commandStorage.getInvalidCommands().get(0));
		assertEquals(INVALID_DEPOSIT_COMMAND, commandStorage.getInvalidCommands().get(1));

	}

	@Test
	void size_of_invalid_commands_is_one_when_adding_one_invalid_command() {
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);
		assertEquals(1, commandStorage.getInvalidCommands().size());

	}

	@Test
	void size_of_invalid_commands_is_two_when_adding_two_invalid_commands() {
		commandStorage.addInvalidCommand(INVALID_DEPOSIT_COMMAND);
		commandStorage.addInvalidCommand(INVALID_CREATE_COMMAND);

		assertEquals(2, commandStorage.getInvalidCommands().size());

	}

}
