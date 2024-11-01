import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);

	}

	@Test
	void create_valid_acc_of_type_checking() {
		boolean actual = commandValidator.validate("create checking 12345678 3.2");
		assertTrue(actual);
	}

	@Test
	void create_valid_acc_of_type_savings() {
		boolean actual = commandValidator.validate("create savings 12345678 3.2");
		assertTrue(actual);
	}

	@Test
	void create_acc_has_invalid_acc_type_that_is_not_cd_checking_or_savings() {
		boolean actual = commandValidator.validate("create account 12345678 3.2");
		assertFalse(actual);
	}

	@Test
	void create_checking_does_not_have_the_correct_amount_of_arguments() {
		boolean actual = commandValidator.validate("create checking 12345678");
		assertFalse(actual);
	}

	@Test
	void create_checking_does_have_the_correct_amount_of_arguments() {
		boolean actual = commandValidator.validate("create checking 12345678 4.1");
		assertTrue(actual);
	}

	@Test
	void create_savings_does_not_have_the_correct_amount_of_arguments() {
		boolean actual = commandValidator.validate("create savings 12345678");
		assertFalse(actual);
	}

	@Test
	void create_savings_does_have_the_correct_amount_of_arguments() {
		boolean actual = commandValidator.validate("create savings 12345678 4.1");
		assertTrue(actual);
	}

	@Test
	void create_cd_does_not_have_the_correct_number_of_arguments() {
		boolean actual = commandValidator.validate("create cd 12345678 3.5");
		assertFalse(actual);
	}

	@Test
	void create_cd_does_have_the_correct_number_of_arguments() {
		boolean actual = commandValidator.validate("create cd 12345678 3.5 100");
		assertTrue(actual);
	}

	@Test
	void create_checking_acc_has_valid_eight_digit_ID_value() {
		boolean actual = commandValidator.validate("create checking 12345678 3.2");
		assertTrue(actual);
	}

	@Test
	void create_checking_acc_has_invalid_non_eight_digit_ID_value() {
		boolean actual = commandValidator.validate("create checking 1234578 3.2");
		assertFalse(actual);
	}

	@Test
	void create_checking_acc_has_non_numeric_ID_value() {
		boolean actual = commandValidator.validate("create checking 123G578 3.2");
		assertFalse(actual);
	}

	@Test
	void create_savings_acc_has_valid_eight_digit_ID_value() {
		boolean actual = commandValidator.validate("create savings 12345678 3.2");
		assertTrue(actual);
	}

	@Test
	void create_savings_acc_has_invalid_non_eight_digit_ID_value() {
		boolean actual = commandValidator.validate("create savings 1234578 3.2");
		assertFalse(actual);
	}

	@Test
	void create_savings_acc_has_non_numeric_ID_value() {
		boolean actual = commandValidator.validate("create savings 123G578 3.2");
		assertFalse(actual);
	}

	@Test
	void create_cd_does_have_valid_eight_digit_ID_value() {
		boolean actual = commandValidator.validate("create cd 12345678 3.5 100");
		assertTrue(actual);
	}

}
