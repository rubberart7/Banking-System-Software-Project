package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void transfer_command_is_case_insensitive() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");
		boolean actual = commandValidator.validate("TRANSFER 12345678 87654321 200");
		assertTrue(actual);

	}

//	valid/invalid idValues

	@Test
	void transfer_command_has_typo_is_invalid() {
		boolean actual = commandValidator.validate("transferr 12345678 87654321 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_has_non_eight_digit_non_numeric_value_for_id_for_the_from_acc_is_invalid() {
		boolean actual = commandValidator.validate("transfer 123A5678 87654321 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_has_id_value_for_the_from_acc_with_an_id_value_with_more_than_eight_digits_is_invalid() {
		boolean actual = commandValidator.validate("transfer 123456789 87654321 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_has_id_value_for_the_from_acc_with_an_id_value_with_less_than_eight_digit_is_invalid() {
		boolean actual = commandValidator.validate("transfer 1234567 87654321 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_has_non_eight_digit_non_numeric_value_for_id_for_the_to_acc_is_invalid() {
		boolean actual = commandValidator.validate("transfer 12345678 876A4321 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_has_id_value_for_the_to_acc_with_an_id_value_with_more_than_eight_digits_is_invalid() {
		boolean actual = commandValidator.validate("transfer 12345678 987654321 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_has_id_value_for_the_to_acc_with_an_id_value_with_less_than_eight_digits_is_invalid() {
		boolean actual = commandValidator.validate("transfer 12345678 8765432 200");
		assertFalse(actual);
	}

	@Test
	void trying_to_transfer_money_when_the_from_acc_does_not_exist_and_the_bank_has_no_accs_makes_the_command_invalid() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertFalse(actual);
	}

	@Test
	void trying_to_transfer_money_when_the_from_acc_does_not_exist_and_the_bank_has_many_other_accs_makes_the_command_invalid() {
		bank.addRegularAccount("12345608", 3.0, "checking");
		bank.addRegularAccount("12345008", 3.0, "savings");
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertFalse(actual);
	}

	@Test
	void trying_to_transfer_money_when_the_from_acc_does_not_exist_and_the_to_account_exists_is_invalid() {
		bank.addRegularAccount("12345078", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertFalse(actual);

	}

	@Test
	void trying_to_transfer_money_when_the_to_acc_does_not_exist_and_the_from_account_exists_is_invalid() {
		bank.addRegularAccount("12345078", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");
		bank.getAccounts().get("87654321").deposit(100);
		boolean actual = commandValidator.validate("transfer 87654321 12345678 100");
		assertFalse(actual);

	}

	@Test
	void trying_to_transfer_money_when_the_from_acc_is_the_only_acc_that_is_in_the_bank_is_invalid() {
		bank.addRegularAccount("87654321", 3.0, "savings");
		bank.getAccounts().get("87654321").deposit(100);
		boolean actual = commandValidator.validate("transfer 87654321 12345678 100");
		assertFalse(actual);
	}

//	missing values/arguments

	@Test
	void trying_to_transfer_money_when_neither_of_the_accs_exist_is_invalid() {
		boolean actual = commandValidator.validate("transfer 87654321 12345678 100");
		assertFalse(actual);
	}

	@Test
	void transfer_command_does_not_have_two_id_values_making_the_command_invalid() {
		boolean actual = commandValidator.validate("transfer 12345678 100");
		assertFalse(actual);
	}

	@Test
	void transfer_command_is_missing_the_transfer_word_making_the_command_invalid() {
		boolean actual = commandValidator.validate("12345678 87654321 100");
		assertFalse(actual);
	}

	@Test
	void transfer_command_has_too_many_arguments_making_the_command_invalid() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100 100");
		assertFalse(actual);
	}

	@Test
	void transfer_command_has_arguments_switched_around_making_the_command_invalid() {
		boolean actual = commandValidator.validate("transfer 12345678 100 87654321");
		boolean actualNew = commandValidator.validate("transfer 100 12345678 87654321");

		assertFalse(actual);
		assertFalse(actualNew);
	}

	@Test
	void transfer_amount_is_not_a_double_making_command_invalid() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 10A");

		assertFalse(actual);
	}

//	valid or invalid transfer amounts
	@Test
	void transfer_amount_is_invalid_because_you_cannot_withdraw_more_than_400_from_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(800);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 401");

		assertFalse(actual);

	}

	@Test
	void transfer_amount_is_invalid_because_you_cannot_withdraw_negative_from_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(800);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 -1");

		assertFalse(actual);

	}

	@Test
	void transfer_amount_is_valid_because_you_can_withdraw_0_from_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(800);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 0.0");

		assertTrue(actual);

	}

	@Test
	void transfer_amount_is_valid_because_you_can_withdraw_a_value_between_0_and_400_from_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(800);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 200");

		assertTrue(actual);

	}

	@Test
	void transfer_amount_is_valid_because_you_can_withdraw_400_from_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(800);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 400");

		assertTrue(actual);

	}

	@Test
	void transfer_amount_is_invalid_because_you_cannot_withdraw_more_than_1000_from_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(1200);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 1001");

		assertFalse(actual);

	}

	@Test
	void transfer_amount_is_invalid_because_you_cannot_withdraw_negative_from_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(1200);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 -1");

		assertFalse(actual);
	}

	@Test
	void transfer_amount_is_valid_because_you_can_withdraw_0_from_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(1200);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 0");

		assertTrue(actual);
	}

	@Test
	void transfer_amount_is_valid_because_you_can_withdraw_the_maximum_of_1000_from_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(1200);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 1000");

		assertTrue(actual);
	}

	@Test
	void transfer_amount_is_valid_because_you_can_withdraw_an_amount_between_0_and_1000_from_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(1200);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 500");

		assertTrue(actual);
	}

//	cd acc cant be apart of transfer of from or to
	@Test
	void transfer_is_invalid_because_you_are_trying_to_withdraw_from_cd_acc() {
		bank.addCDAccount("12345678", 0.0, 1200);
		bank.addRegularAccount("87654321", 0.0, "checking");
		bank.getAccounts().get("87654321").deposit(100);
		bank.passTime(12);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 1200");

		assertFalse(actual);

	}

	@Test
	void transfer_is_invalid_because_you_are_trying_to_transfer_to_a_cd_acc() {
		bank.addCDAccount("12345678", 0.0, 1000);
		bank.addRegularAccount("87654321", 0.0, "checking");
		bank.getAccounts().get("87654321").deposit(1000);
		bank.passTime(12);

		boolean actual = commandValidator.validate("transfer 87654321 12345678 1200");

		assertFalse(actual);

	}

// can transfer between checking and savings acc
	@Test
	void transfer_is_valid_because_you_are_transferring_from_checking_acc_to_another_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 80");

		assertTrue(actual);
	}

	@Test
	void transfer_is_valid_because_you_are_transferring_entire_balance_from_checking_acc_to_another_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");

		assertTrue(actual);
	}

	@Test
	void transfer_transfer_is_valid_because_you_are_transferring_from_checking_acc_to_a_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 80");

		assertTrue(actual);
	}

	@Test
	void transfer_is_valid_because_you_are_transferring_entire_balance_from_checking_acc_to_a_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");

		assertTrue(actual);
	}

	@Test
	void transfer_is_valid_because_you_are_transferring_from_a_savings_acc_to_another_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 80");

		assertTrue(actual);
	}

	@Test
	void transfer_is_valid_because_you_are_transferring_the_entire_balance_from_a_savings_acc_to_another_savings_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");

		assertTrue(actual);
	}

	@Test
	void transfer_is_valid_because_you_are_transferring_from_a_savings_acc_to_a_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 80");

		assertTrue(actual);
	}

	@Test
	void transfer_is_valid_because_you_are_transferring_the_entire_balance_from_a_savings_acc_to_a_checking_acc() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");

		assertTrue(actual);
	}

	@Test
	void transfer_from_checking_is_valid_even_if_you_try_to_transfer_more_than_what_is_in_the_account() {
		bank.addRegularAccount("12345678", 3.0, "checking");
		bank.addRegularAccount("87654321", 3.0, "savings");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 400");

		assertTrue(actual);
	}

	@Test
	void transfer_from_savings_is_valid_even_if_you_try_to_transfer_more_than_what_is_in_the_account() {
		bank.addRegularAccount("12345678", 3.0, "savings");
		bank.addRegularAccount("87654321", 3.0, "checking");

		bank.getAccounts().get("12345678").deposit(100);

		boolean actual = commandValidator.validate("transfer 12345678 87654321 400");

		assertTrue(actual);
	}

	@Test
	void transfer_to_and_from_the_same_acc_is_invalid() {
		boolean actual = commandValidator.validate("transfer 12345678 12345678 200");
		assertFalse(actual);
	}

}
