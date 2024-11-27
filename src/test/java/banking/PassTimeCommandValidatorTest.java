package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);

	}

	@Test
	void pass_time_command_is_missing_month_value_is_invalid() {
		boolean actual = commandValidator.validate("pass");
		assertFalse(actual);
	}

	@Test
	void pass_time_that_is_missing_pass_part_of_command_is_invalid() {
		boolean actual = commandValidator.validate("2");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_arguments_swapped_around_is_invalid() {
		boolean actual = commandValidator.validate("2 pass");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_has_month_value_that_is_an_integer_between_1_and_60() {
		boolean actual = commandValidator.validate("pass 2");
		assertTrue(actual);

	}

	@Test
	void pass_time_has_month_value_that_is_1_is_valid() {
		boolean actual = commandValidator.validate("pass 1");
		assertTrue(actual);
	}

	@Test
	void pass_time_has_month_value_that_is_60_is_valid() {
		boolean actual = commandValidator.validate("pass 60");
		assertTrue(actual);
	}

	@Test
	void pass_time_above_60_is_invalid() {
		boolean actual = commandValidator.validate("pass 61");
		assertFalse(actual);
	}

	@Test
	void pass_time_below_1_is_invalid() {
		boolean actual = commandValidator.validate("pass 0");
		assertFalse(actual);
	}

	@Test
	void pass_time_typo_in_pass_part_is_invalid() {
		boolean actual = commandValidator.validate("paass 5");
		assertFalse(actual);

	}

	@Test
	void pass_time_typo_in_months_part_is_invalid() {
		boolean actual = commandValidator.validate("pass 5A");
		assertFalse(actual);
	}

	@Test
	void pass_time_month_value_as_double_is_invalid() {
		boolean actual = commandValidator.validate("pass 5.2");
		assertFalse(actual);
	}

}
