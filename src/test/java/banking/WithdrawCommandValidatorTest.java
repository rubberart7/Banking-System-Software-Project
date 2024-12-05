package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandValidatorTest {

	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

// tests for range of withdrawal values
	@Test
	void withdraw_between_0_and_1000_is_for_checking_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("withdraw 12345678 100");
		assertTrue(actual);
	}

	@Test
	void withdraw_0_from_checking_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	void withdraw_400_from_checking_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("withdraw 12345678 400");
		assertTrue(actual);
	}

	@Test
	void withdraw_more_than_400_from_checking_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("withdraw 12345678 401");
		assertFalse(actual);
	}

	@Test
	void withdraw_between_0_and_1000_for_savings_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 12345678 401");
		assertTrue(actual);
	}

	@Test
	void trying_to_withdraw_more_than_once_in_the_first_month_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actualOne = commandValidator.validate("withdraw 12345678 401");
		bank.getAccounts().get("12345678").addMonthlyWithdrawal(1);
		boolean actualTwo = commandValidator.validate("withdraw 12345678 401");

		assertTrue(actualOne);
		assertFalse(actualTwo);
	}

	@Test
	void withdrawing_again_from_savings_after_one_month_has_passed_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actualOne = commandValidator.validate("withdraw 12345678 401");
		bank.getAccounts().get("12345678").addMonthlyWithdrawal(1);
		bank.getAccounts().get("12345678").addTime(1);
		boolean actualTwo = commandValidator.validate("withdraw 12345678 401");

		assertTrue(actualOne);
		assertTrue(actualTwo);
	}

	@Test
	void trying_to_withdraw_from_savings_in_month_four_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		bank.getAccounts().get("12345678").addTime(3);
		boolean actualOne = commandValidator.validate("withdraw 12345678 401");
		bank.getAccounts().get("12345678").addMonthlyWithdrawal(1);
		boolean actualTwo = commandValidator.validate("withdraw 12345678 401");

		assertTrue(actualOne);
		assertFalse(actualTwo);

	}

	@Test
	void withdraw_0_for_savings_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	void withdraw_1000_for_savings_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 12345678 1000");
		assertTrue(actual);
	}

	// test typos/missing values/case insensitive/invalid values

	@Test
	void withdraw_over_1000_from_savings_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 12345678 1001");
		assertFalse(actual);
	}

	@Test
	void withdraw_negative_amount_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("withdraw 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_is_missing() {
		boolean actual = commandValidator.validate("withdraw 12345678");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_has_typo() {
		boolean actual = commandValidator.validate("withdraww 12345678 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_is_missing() {
		boolean actual = commandValidator.validate("12345678 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_is_case_insensitive() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 12345678 100");
		assertTrue(actual);
	}

	@Test
	void withdraw_savings_has_too_many_arguments_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 1235678 1000 500");
		assertFalse(actual);

	}

	@Test
	void withdraw_savings_with_missing_withdraw_amount_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 1235678");
		assertFalse(actual);
	}

	@Test
	void withdraw_checking_has_too_many_arguments_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("withdraw 1235678 1000 500");
		assertFalse(actual);

	}

	@Test
	void withdraw_checking_with_missing_withdraw_amount_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("withdraw 1235678");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_arguments_are_out_of_order_is_invalid() {
		boolean actual = commandValidator.validate("100 withdraw 1235678");
		assertFalse(actual);
	}

	// checking ID validation
	@Test
	void withdraw_id_is_missing_is_invalid() {
		boolean actual = commandValidator.validate("deposit 100");
		assertFalse(actual);

	}

	@Test
	void withdraw_command_has_non_eight_digit_ID_is_invalid() {
		boolean actual = commandValidator.validate("deposit 1235678 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_has_non_numeric_eight_digit_ID() {
		boolean actual = commandValidator.validate("withdraw 123G6S78 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_has_all_valid_values_and_spelling() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 12345678 100");
		assertTrue(actual);
	}

	// cd account conditions
	@Test
	void withdraw_full_balance_from_cd_acc_after_12_months_have_passed_is_valid() {
		bank.addCDAccount("12345678", 5.1, 1000);
		bank.passTime(12);

		boolean actual = commandValidator.validate("withdraw 12345678 1225.77");

		assertTrue(actual);
	}

	@Test
	void withdraw_more_than_full_balance_from_cd_acc_after_12_months_have_passed_is_valid() {
		bank.addCDAccount("12345678", 5.1, 1000);
		bank.passTime(12);

		boolean actual = commandValidator.validate("withdraw 12345678 20000.00");

		assertTrue(actual);
	}

	@Test
	void withdraw_full_balance_before_12_months_have_passed_is_invalid() {
		bank.addCDAccount("12345678", 5.1, 1000);
		bank.passTime(11);

		boolean actual = commandValidator.validate("withdraw 12345678 1205.15");

		assertFalse(actual);

	}

	@Test
	void not_withdrawing_full_balance_from_cd_after_12_months_has_passed_is_invalid() {
		bank.addCDAccount("12345678", 5.1, 1000);
		bank.passTime(12);
		boolean actual = commandValidator.validate("withdraw 12345678 1224.77");

		assertFalse(actual);
	}

	@Test
	void withdrawing_negative_amount_from_cd_acc_after_12_months_is_invalid() {
		bank.addCDAccount("12345678", 5.1, 1000);
		bank.passTime(12);

		boolean actual = commandValidator.validate("withdraw 12345678 -1");

		assertFalse(actual);

	}

}
