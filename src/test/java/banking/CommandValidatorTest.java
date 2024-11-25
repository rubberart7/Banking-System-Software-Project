package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);

	}

	@Test
	void command_validator_can_confirm_that_empty_command_is_invalid() {
		boolean actual = commandValidator.validate("");
		assertFalse(actual);
	}

	@Test
	void command_validator_can_confirm_that_a_create_command_is_invalid() {
		boolean actual = commandValidator.validate("create checkiing 12345678 3.2");
		assertFalse(actual);

	}

	@Test
	void command_validator_can_confirm_that_a_create_command_is_invalid_twice() {
		boolean actual = commandValidator.validate("create checkiing 12345678 3.2");
		boolean actualTwo = commandValidator.validate("create savingss 12345678 3.2");
		assertFalse(actual);
		assertFalse(actualTwo);

	}

	@Test
	void command_validator_can_confirm_that_a_create_command_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 3.2");
		assertTrue(actual);

	}

	@Test
	void command_validator_can_confirm_that_a_create_command_is_valid_twice() {
		boolean actual = commandValidator.validate("create checking 12345678 3.2");
		boolean actualTwo = commandValidator.validate("create savings 12345678 3.2");

		assertTrue(actual);
		assertTrue(actualTwo);

	}

	@Test
	void command_validator_can_confirm_that_a_deposit_command_is_invalid() {
		boolean actual = commandValidator.validate("deposiit 12345678 100");
		assertFalse(actual);

	}

	@Test
	void command_validator_can_confirm_that_a_deposit_command_is_invalid_twice() {
		boolean actual = commandValidator.validate("deposiit 12345678 100");
		boolean actualTwo = commandValidator.validate("depositt 12345678 100");
		assertFalse(actual);
		assertFalse(actualTwo);

	}

	@Test
	void command_validator_can_confirm_that_a_deposit_command_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 12345678 100");
		boolean actualTwo = commandValidator.validate("deposit 12345678 100");
		assertTrue(actual);
		assertTrue(actualTwo);

	}

}
