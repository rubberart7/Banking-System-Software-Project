package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);

	}

//    cant deposit values outside ranges
	@Test
	void deposit_negative_amount_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void deposit_more_than_1000_in_checking_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 12345678 1001");
		assertFalse(actual);
	}

	@Test
	void deposit_between_0_and_1000_in_checking_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 12345678 1");
		assertTrue(actual);
	}

	@Test
	void deposit_1000_in_checking_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void deposit_0_in_checking_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	void deposit_more_than_2500_in_savings_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("deposit 12345678 2501");
		assertFalse(actual);
	}

	@Test
	void deposit_between_0_and_2500_in_savings_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("deposit 12345678 1");
		assertTrue(actual);
	}

	@Test
	void deposit_2500_in_savings_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("deposit 12345678 2500");
		assertTrue(actual);
	}

	@Test
	void deposit_0_in_savings_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	void deposit_money_into_cd_is_invalid() {
		bank.addCDAccount("12345678", 2.1, 100);
		boolean actual = commandValidator.validate("deposit 12345678 100");
		assertFalse(actual);

	}

	// test typos/missing values/case insensitive/invalid values
	@Test
	void deposit_amount_is_missing() {
		boolean actual = commandValidator.validate("deposit 12345678");
		assertFalse(actual);

	}

	@Test
	void deposit_command_has_typo() {
		boolean actual = commandValidator.validate("depsit 12345678 100");
		assertFalse(actual);
	}

	@Test
	void deposit_command_is_missing() {
		boolean actual = commandValidator.validate("12345678 100");
		assertFalse(actual);

	}

	@Test
	void deposit_command_is_case_insensitive() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("DEPOSIT 12345678 100");
		assertTrue(actual);
	}

	@Test
	void deposit_command_has_non_string_values() {
		boolean actual = commandValidator.validate("deposit234 12345678 100");
		assertFalse(actual);
	}

	@Test
	void deposit_savings_has_too_many_arguments() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("deposit 1235678 1000 500");
		assertFalse(actual);

	}

	@Test
	void deposit_savings_is_missing_deposit_amount() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("deposit 1235678");
		assertFalse(actual);

	}

	@Test
	void deposit_checking_has_too_many_arguments() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 1235678 1000 500");
		assertFalse(actual);

	}

	@Test
	void deposit_checking_is_missing_deposit_amount() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("deposit 1235678");
		assertFalse(actual);

	}

	@Test
	void deposit_amount_contains_non_numeric_values() {
		boolean actual = commandValidator.validate("deposit 1235678 100a");
		assertFalse(actual);
	}

	@Test
	void deposit_command_arguments_are_out_of_order_is_invalid() {
		boolean actual = commandValidator.validate("100 deposit 1235678");
		assertFalse(actual);
	}

//	checking ID validation
	@Test
	void deposit_id_is_missing() {
		boolean actual = commandValidator.validate("deposit 100");
		assertFalse(actual);

	}

	@Test
	void deposit_command_has_non_eight_digit_ID() {
		boolean actual = commandValidator.validate("deposit 1235678 100");
		assertFalse(actual);
	}

	@Test
	void deposit_command_has_non_numeric_eight_digit_ID() {
		boolean actual = commandValidator.validate("deposit 123G6S78 100");
		assertFalse(actual);
	}

	@Test
	void deposit_command_has_all_valid_values_and_spelling() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("deposit 12345678 100");
		assertTrue(actual);
	}

}
