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

	// test with missing arguments
	@Test
	void create_checking_acc_with_missing_create_command() {
		boolean actual = commandValidator.validate("checking 12345678 3.2");
		assertFalse(actual);
	}

	@Test
	void create_checking_with_missing_apr_value() {
		boolean actual = commandValidator.validate("create checking 12345678");
		assertFalse(actual);

	}

	@Test
	void create_savings_with_missing_apr_value() {
		boolean actual = commandValidator.validate("create savings 12345678");
		assertFalse(actual);

	}

	@Test
	void create_cd_with_missing_apr_value() {
		boolean actual = commandValidator.validate("create cd 12345678 1000");
		assertFalse(actual);

	}

	@Test
	void create_checking_with_missing_id_value() {
		boolean actual = commandValidator.validate("create checking 3.2");
		assertFalse(actual);

	}

	@Test
	void create_savings_with_missing_id_value() {
		boolean actual = commandValidator.validate("create savings 3.2");
		assertFalse(actual);

	}

	@Test
	void create_cd_with_missing_id_value() {
		boolean actual = commandValidator.validate("create cd 3.2 1000");
		assertFalse(actual);

	}

	@Test
	void create_command_arguments_out_of_order() {
		boolean actual = commandValidator.validate("12345678 checking 3.2");
		assertFalse(actual);

	}

	@Test
	void create_checking_acc_with_typo_create_command() {
		boolean actual = commandValidator.validate("crate checking 12345678 3.2");
		assertFalse(actual);
	}

	@Test
	void create_savings_acc_with_missing_create_command() {
		boolean actual = commandValidator.validate("savings 12345678 3.2");
		assertFalse(actual);
	}

	@Test
	void create_cd_acc_with_missing_create_command() {
		boolean actual = commandValidator.validate("cd 3.2 1000");
		assertFalse(actual);
	}

	@Test
	void create_cd_acc_with_missing_minimum_value() {
		boolean actual = commandValidator.validate("cd 12345678 3.2");
		assertFalse(actual);
	}

	@Test
	void create_checking_or_savings_and_missing_account_type() {
		boolean actual = commandValidator.validate("create 12345678 3.2");
		assertFalse(actual);
	}

	@Test
	void create_cd_account_with_missing_account_type() {
		boolean actual = commandValidator.validate("create 12345678 3.2 1000");
		assertFalse(actual);
	}

// create account with valid acc type or not
	@Test
	void create_all_valid_values_of_acc_of_type_checking() {
		boolean actual = commandValidator.validate("create checking 12345678 3.2");
		assertTrue(actual);
	}

	@Test
	void create_all_valid_values_of_acc_of_type_savings() {
		boolean actual = commandValidator.validate("create savings 12345678 3.2");
		assertTrue(actual);
	}

	@Test
	void create_all_valid_values_of_acc_of_type_cd() {
		boolean actual = commandValidator.validate("create cd 12345678 3.2 10000");
		assertTrue(actual);
	}

	@Test
	void create_acc_has_invalid_acc_type_that_is_not_cd_checking_or_savings() {
		boolean actual = commandValidator.validate("create account 12345678 3.2");
		assertFalse(actual);
	}

//	create accs with correct amount of arguments or not
	@Test
	void create_checking_does_have_the_correct_amount_of_arguments() {
		boolean actual = commandValidator.validate("create checking 12345678 4.1");
		assertTrue(actual);
	}

	@Test
	void create_checking_has_too_few_arguments() {
		boolean actual = commandValidator.validate("create checking 12345678");
		assertFalse(actual);
	}

	@Test
	void create_checking_has_too_many_arguments() {
		boolean actual = commandValidator.validate("create checking 12345678 4.1 1000");
		assertFalse(actual);
	}

	@Test
	void create_savings_does_have_the_correct_amount_of_arguments() {
		boolean actual = commandValidator.validate("create savings 12345678 4.1");
		assertTrue(actual);
	}

	@Test
	void create_savings_has_too_few_arguments() {
		boolean actual = commandValidator.validate("create savings 12345678");
		assertFalse(actual);
	}

	@Test
	void create_savings_has_too_many_arguments() {
		boolean actual = commandValidator.validate("create savings 12345678 4.1 1000");
		assertFalse(actual);
	}

	@Test
	void create_cd_does_have_the_correct_number_of_arguments() {
		boolean actual = commandValidator.validate("create cd 12345678 3.5 1000");
		assertTrue(actual);
	}

	@Test
	void create_cd_has_too_few_arguments() {
		boolean actual = commandValidator.validate("create cd 12345678 3.5");
		assertFalse(actual);
	}

	@Test
	void create_cd_has_too_many_argument() {
		boolean actual = commandValidator.validate("create cd 12345678 3.5 1000 2000");
		assertFalse(actual);
	}

//	create command with valid or invalid ID values
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
		boolean actual = commandValidator.validate("create cd 12345678 3.5 1000");
		assertTrue(actual);
	}

	@Test
	void create_cd_has_invalid_non_eight_digit_ID_value() {
		boolean actual = commandValidator.validate("create cd 1245678 3.5 1000");
		assertFalse(actual);
	}

	@Test
	void create_cd_has_non_numeric_ID_value() {
		boolean actual = commandValidator.validate("create cd 12L5G78 3.5 1000");
		assertFalse(actual);
	}

//	valid APR values
	@Test
	void create_checking_with_valid_apr_value_within_range() {
		boolean actual = commandValidator.validate("create checking 12345678 9.9");
		assertTrue(actual);
	}

	@Test
	void create_checking_acc_with_apr_value_above_range() {
		boolean actual = commandValidator.validate("create checking 12345678 10.1");
		assertFalse(actual);
	}

	@Test
	void create_checking_acc_with_negative_apr_value() {
		boolean actual = commandValidator.validate("create checking 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void create_savings_with_valid_apr_value_within_range() {
		boolean actual = commandValidator.validate("create savings 12345678 9.9");
		assertTrue(actual);
	}

	@Test
	void create_savings_acc_with_value_above_range() {
		boolean actual = commandValidator.validate("create savings 12345678 10.1");
		assertFalse(actual);
	}

	@Test
	void create_savings_acc_with_negative_value() {
		boolean actual = commandValidator.validate("create savings 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void create_cd_acc_with_apr_value_within_range() {
		boolean actual = commandValidator.validate("create cd 12345678 9.9 1000");
		assertTrue(actual);
	}

	@Test
	void create_cd_acc_with_apr_value_above_range() {
		boolean actual = commandValidator.validate("create cd 12345678 10.1 100");
		assertFalse(actual);
	}

	@Test
	void create_cd_acc_with_negative_apr_value() {
		boolean actual = commandValidator.validate("create cd 12345678 -1 100");
		assertFalse(actual);
	}

//	command is case-insensitive
	@Test
	void create_checking_acc_case_insensitive() {
		boolean actual = commandValidator.validate("CREATE CHECKING 12345678 9.9");
		assertTrue(actual);

	}

	@Test
	void create_savings_acc_case_insensitive() {
		boolean actual = commandValidator.validate("CREATE SAVINGS 12345678 9.9");
		assertTrue(actual);

	}

	@Test
	void create_cd_acc_case_insensitive() {
		boolean actual = commandValidator.validate("CREATE CD 12345678 9.9 1000");
		assertTrue(actual);
	}

//	cd acc minimum value validations
	@Test
	void create_cd_acc_with_min_value_in_range() {
		boolean actual = commandValidator.validate("CREATE CD 12345678 9.9 10000");
		assertTrue(actual);
	}

	@Test
	void create_cd_acc_with_min_value_over_range() {
		boolean actual = commandValidator.validate("CREATE CD 12345678 9.9 10001");
		assertFalse(actual);
	}

	@Test
	void create_cd_acc_with_min_value_below_range() {
		boolean actual = commandValidator.validate("CREATE CD 12345678 9.9 999");
		assertFalse(actual);
	}

	@Test
	void create_cd_acc_with_min_value_being_negative() {
		boolean actual = commandValidator.validate("CREATE CD 12345678 9.9 -1");
		assertFalse(actual);
	}

//	checking and savings acc no minimum value
	@Test
	void create_checking_account_without_given_minimum_value_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 2.1");
		assertTrue(actual);
	}

	@Test
	void create_checking_account_with_given_minimum_value_is_invalid() {
		boolean actual = commandValidator.validate("create checking 12345678 2.1 1000");
		assertFalse(actual);
	}

	@Test
	void create_savings_account_without_given_minimum_value_is_valid() {
		boolean actual = commandValidator.validate("create savings 12345678 2.1");
		assertTrue(actual);
	}

	@Test
	void create_savings_account_with_given_minimum_value_is_invalid() {
		boolean actual = commandValidator.validate("create savings 12345678 2.1 1000");
		assertFalse(actual);
	}

//	test the unique ID values
	@Test
	void create_account_with_duplicate_id_values_is_invalid() {
		bank.addRegularAccount("12345678", 2.0, "CheckingAccount");
		boolean actual = commandValidator.validate("create checking 12345678 2.2");

		assertFalse(actual);
	}

}
