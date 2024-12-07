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

	@Test
	void withdrawing_from_acc_that_does_not_exist_when_there_are_no_accs_in_the_bank_is_invalid() {
		boolean actual = commandValidator.validate("withdraw 12345678 100");
		assertFalse(actual);
	}

	@Test
	void withdrawing_from_acc_that_does_not_exist_when_there_are_some_accs_in_the_bank_is_invalid() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("12345687", 3.0, "savings");
		boolean actual = commandValidator.validate("withdraw 87654321 100");
		assertFalse(actual);
	}

	@Test
	void withdrawing_from_a_checking_acc_that_does_exists_when_it_is_the_only_acc_is_valid() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.getAccounts().get("12345678").deposit(500);
		boolean actual = commandValidator.validate("withdraw 12345678 100");

		assertTrue(actual);
	}

	@Test
	void withdrawing_from_a_checking_acc_that_does_exist_when_there_are_many_accs_is_valid() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "checking");
		bank.getAccounts().get("12345678").deposit(500);
		boolean actual = commandValidator.validate("withdraw 12345678 100");

		assertTrue(actual);
	}

	@Test
	void withdrawing_from_a_savings_acc_that_does_exist_when_it_is_the_only_acc_is_valid() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.getAccounts().get("12345678").deposit(500);
		boolean actual = commandValidator.validate("withdraw 12345678 100");
		assertTrue(actual);
	}

	@Test
	void withdrawing_from_a_savings_acc_that_does_exist_when_there_are_many_accs_is_valid() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");
		bank.getAccounts().get("12345678").deposit(500);
		boolean actual = commandValidator.validate("withdraw 12345678 100");

		assertTrue(actual);
	}

// tests for range of withdrawal values
	@Test
	void withdraw_between_0_and_400_for_checking_acc_is_valid() {
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
	void trying_to_withdraw_more_than_once_from_savings_acc_in_the_first_month_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actualOne = commandValidator.validate("withdraw 12345678 401");
		bank.getAccounts().get("12345678").withdraw(401);
		boolean actualTwo = commandValidator.validate("withdraw 12345678 401");

		assertTrue(actualOne);
		assertFalse(actualTwo);
	}

	@Test
	void withdrawing_again_from_savings_after_one_month_has_passed_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		bank.getAccounts().get("12345678").deposit(1000);
		boolean actualOne = commandValidator.validate("withdraw 12345678 401");
		bank.getAccounts().get("12345678").withdraw(401);
		bank.passTime(1);
		boolean actualTwo = commandValidator.validate("withdraw 12345678 401");

		assertTrue(actualOne);
		assertTrue(actualTwo);
	}

	@Test
	void trying_to_withdraw_from_savings_acc_twice_in_month_four_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		bank.getAccounts().get("12345678").deposit(1000);
		bank.passTime(3);
		boolean actualOne = commandValidator.validate("withdraw 12345678 401");
		bank.getAccounts().get("12345678").withdraw(401);
		boolean actualTwo = commandValidator.validate("withdraw 12345678 401");

		assertTrue(actualOne);
		assertFalse(actualTwo);

	}

	@Test
	void trying_to_withdraw_once_in_month_four_and_then_again_2_months_later_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		bank.getAccounts().get("12345678").deposit(1000);
		bank.passTime(3);
		boolean actualOne = commandValidator.validate("withdraw 12345678 401");
		bank.getAccounts().get("12345678").withdraw(401);
		bank.passTime(2);
		boolean actualTwo = commandValidator.validate("withdraw 12345678 401");

		assertTrue(actualOne);
		assertTrue(actualTwo);
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

	@Test
	void withdraw_over_1000_from_savings_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
		boolean actual = commandValidator.validate("withdraw 12345678 1001");
		assertFalse(actual);
	}

	// test typos/missing values/case insensitive/invalid values

	@Test
	void withdraw_negative_amount_from_checking_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "checking");
		boolean actual = commandValidator.validate("withdraw 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void withdraw_negative_amount_from_savings_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "savings");
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
		boolean actual = commandValidator.validate("WItHDRaw 12345678 100");
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
	void withdraw_command_has_non_numeric_eight_digit_ID_makes_the_command_invalid() {
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
	void withdraw_full_balance_after_more_than_12_months_have_passed_is_valid() {
		bank.addCDAccount("12345678", 0.0, 1000);
		bank.passTime(51);

		boolean actual = commandValidator.validate("withdraw 12345678 1000");

		assertTrue(actual);

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

	@Test
	void withdrawing_more_than_the_balance_from_checking_acc_is_still_valid() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("withdraw 12345678 400");
		assertTrue(actual);
	}

	@Test
	void withdrawing_more_than_the_balance_from_savings_acc_is_still_valid() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("withdraw 12345678 400");
		assertTrue(actual);
	}

}
