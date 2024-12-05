package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

// increasing the age properly
	@Test
	void pass_time_increases_the_age_of_the_bank_properly() {
		commandProcessor.processCommand("pass 9");
		assertEquals(9, bank.getAge());

	}

	@Test
	void pass_time_of_of_the_minimum_of_one_month_increases_the_age_of_the_bank_by_one_month() {
		commandProcessor.processCommand("pass 1");

		assertEquals(1, bank.getAge());
	}

	@Test
	void pass_time_of_the_maximum_of_61_months_makes_the_age_of_the_bank_increase_by_61_months() {
		commandProcessor.processCommand("pass 61");
		assertEquals(61, bank.getAge());

	}

	@Test
	void pass_time_of_of_the_minimum_of_one_month_increases_the_age_of_the_checking_acc_by_one_month() {
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");

		commandProcessor.processCommand("pass 1");

		assertEquals(1, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void pass_time_of_of_the_maximum_of_61_months_increases_the_age_of_the_checking_acc_by_61_months() {
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");

		commandProcessor.processCommand("pass 61");

		assertEquals(61, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void pass_time_of_of_the_minimum_of_one_month_increases_the_age_of_the_savings_acc_by_one_month() {
		commandProcessor.processCommand("create savings 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");

		commandProcessor.processCommand("pass 1");

		assertEquals(1, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void pass_time_of_of_the_maximum_of_61_months_increases_the_age_of_the_savings_acc_by_61_months() {
		commandProcessor.processCommand("create savings 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");

		commandProcessor.processCommand("pass 61");

		assertEquals(61, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void pass_time_of_minimum_of_one_month_increase_the_age_of_the_cd_account_by_one_month() {
		commandProcessor.processCommand("create cd 12345678 3.0 1000");
		commandProcessor.processCommand("pass 1");

		assertEquals(1, bank.getAccounts().get("12345678").getAge());

	}

	@Test
	void pass_time_of_maximum_of_61_months_increase_the_age_of_the_cd_account_by_61_months() {
		commandProcessor.processCommand("create cd 12345678 3.0 1000");
		commandProcessor.processCommand("pass 61");

		assertEquals(61, bank.getAccounts().get("12345678").getAge());

	}

	@Test
	void pass_time_increases_the_age_of_the_checking_acc_properly() {
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 100");

		commandProcessor.processCommand("pass 3");

		assertEquals(3, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_checking_acc_is_zero_even_if_the_bank_has_been_around_longer() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create checking 12345678 3.0");

		assertEquals(12, bank.getAge());
		assertEquals(0, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_checking_acc_is_younger_than_the_bank_but_is_still_older_than_zero_after_one_month_has_passed_after_creating_the_account() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 900");
		commandProcessor.processCommand("pass 1");

		assertEquals(13, bank.getAge());
		assertEquals(1, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_checking_acc_is_younger_than_the_bank_but_is_still_older_than_zero_after_a_few_months_have_passed_after_creating_the_account() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 900");
		commandProcessor.processCommand("pass 8");

		assertEquals(20, bank.getAge());
		assertEquals(8, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void pass_time_increases_the_age_of_the_savings_acc_properly() {
		commandProcessor.processCommand("create savings 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("pass 3");

		assertEquals(3, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_savings_acc_is_zero_even_if_the_bank_has_been_around_longer() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create savings 12345678 3.0");

		assertEquals(12, bank.getAge());
		assertEquals(0, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_savings_acc_is_younger_than_the_bank_but_is_still_older_than_zero_after_one_month_has_passed_after_creating_the_account() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create savings 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 900");
		commandProcessor.processCommand("pass 1");

		assertEquals(13, bank.getAge());
		assertEquals(1, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_savings_acc_is_younger_than_the_bank_but_is_still_older_than_zero_after_a_few_months_have_passed_after_creating_the_account() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create savings 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 900");
		commandProcessor.processCommand("pass 8");

		assertEquals(20, bank.getAge());
		assertEquals(8, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void pass_time_increases_the_age_of_the_cd_acc_properly() {
		commandProcessor.processCommand("create cd 12345678 3.0 1200");
		commandProcessor.processCommand("pass 3");

		assertEquals(3, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_cd_acc_is_zero_even_if_the_bank_has_been_around_longer() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create cd 12345678 3.0 1200");

		assertEquals(12, bank.getAge());
		assertEquals(0, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_cd_acc_is_younger_than_the_bank_but_is_still_older_than_zero_after_one_month_has_passed_after_creating_the_account() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create cd 12345678 3.0 1200");
		commandProcessor.processCommand("pass 1");

		assertEquals(13, bank.getAge());
		assertEquals(1, bank.getAccounts().get("12345678").getAge());
	}

	@Test
	void the_age_of_a_cd_acc_is_younger_than_the_bank_but_is_still_older_than_zero_after_a_few_months_have_passed_after_creating_the_account() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("create cd 12345678 3.0 1200");
		commandProcessor.processCommand("pass 8");

		assertEquals(20, bank.getAge());
		assertEquals(8, bank.getAccounts().get("12345678").getAge());
	}

//	pass time calculations and deductions

	@Test
	void pass_time_of_one_month_for_checking_acc_with_balance_of_1000_and_apr_of_3_percent_calculates_correct_new_increased_balance() {
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");
		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(1002.509, actualBalance, 0.01);
	}

	@Test
	void pass_time_of_three_months_for_checking_acc_with_balance_of_1000_and_apr_of_3_percent_calculates_correct_new_increased_balance() {
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");
		commandProcessor.processCommand("pass 3");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(1007.52, actualBalance, 0.01);
	}

	@Test
	void pass_time_for_savings_acc_with_a_balance_of_5000_and_apr_of_0_point_6_after_one_month_has_passed_has_correct_new_increased_balance() {
		commandProcessor.processCommand("create savings 12345678 0.6");
		commandProcessor.processCommand("deposit 12345678 5000");
		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(5002.509, actualBalance, 0.01);
	}

	@Test
	void pass_time_of_one_month_for_savings_acc_with_balance_of_1000_and_apr_of_3_percent_calculates_correct_new_increased_balance() {
		commandProcessor.processCommand("create savings 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");
		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(1002.509, actualBalance, 0.01);
	}

	@Test
	void pass_time_of_three_months_for_savings_acc_with_balance_of_1000_and_apr_of_3_percent_calculates_correct_new_increased_balance() {
		commandProcessor.processCommand("create savings 12345678 3.0");
		commandProcessor.processCommand("deposit 12345678 1000");
		commandProcessor.processCommand("pass 3");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(1007.52, actualBalance, 0.01);
	}

	@Test
	void pass_time_for_checking_acc_with_a_balance_of_5000_and_apr_of_0_point_6_after_one_month_has_passed_has_correct_new_increased_balance() {
		commandProcessor.processCommand("create checking 12345678 0.6");
		commandProcessor.processCommand("deposit 12345678 5000");
		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(5002.509, actualBalance, 0.01);
	}

	@Test
	void pass_time_of_one_month_for_cd_acc_with_balance_of_2000_and_two_point_one_percent_apr_calculates_correct_new_increased_balance() {
		commandProcessor.processCommand("create cd 12345678 2.1 2000");
		commandProcessor.processCommand("pass 1");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(2014.036792893758, actualBalance, 0.01);

	}

	@Test
	void pass_time_of_three_months_for_cd_acc_with_balance_of_2000_and_two_point_one_percent_apr_calculates_correct_new_increased_balance() {
		commandProcessor.processCommand("create cd 12345678 2.1 2000");
		commandProcessor.processCommand("pass 3");

		double actualBalance = bank.getAccounts().get("12345678").getBalance();
		assertEquals(2042.41, actualBalance, 0.01);

	}

// code involving removal of accounts
	@Test
	void remove_checking_acc_if_balance_is_0_from_the_start_and_one_month_passes() {
		commandProcessor.processCommand("create checking 12345678 3.0");
		commandProcessor.processCommand("pass 1");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

	@Test
	void remove_savings_acc_if_balance_is_0_from_the_start_and_one_month_passes() {
		bank.addRegularAccount("12345678", 2.5, "savings");
		commandProcessor.processCommand("pass 1");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

	@Test
	void pass_time_for_checking_acc_causes_it_to_be_removed_after_deducting_balance_over_time() {
		commandProcessor.processCommand("create checking 12345678 2.1");
		commandProcessor.processCommand("deposit 12345678 99");

		commandProcessor.processCommand("pass 5");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

	@Test
	void pass_time_for_savings_acc_causes_it_to_be_removed_after_deducting_balance_over_time() {
		commandProcessor.processCommand("create savings 12345678 2.1");
		commandProcessor.processCommand("deposit 12345678 99");

		commandProcessor.processCommand("pass 5");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

	@Test
	void pass_time_for_checking_acc_with_high_apr_causes_it_to_be_removed_after_deducting_balance_over_time() {
		commandProcessor.processCommand("create checking 12345678 10.0");
		commandProcessor.processCommand("deposit 12345678 99");

		commandProcessor.processCommand("pass 6");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

	@Test
	void pass_time_for_savings_acc_with_high_apr_causes_it_to_be_removed_after_deducting_balance_over_time() {
		commandProcessor.processCommand("create savings 12345678 10.0");
		commandProcessor.processCommand("deposit 12345678 99");

		commandProcessor.processCommand("pass 6");
		boolean exists = bank.accountExistsById("12345678");

		assertFalse(exists);
	}

//	pass time balance reductions with being under 100 dollars
	@Test
	void pass_time_reduces_the_balance_of_checking_acc_by_25_dollars_after_one_month_if_the_balance_is_below_100_from_the_start() {
		commandProcessor.processCommand("create checking 12345678 0");
		commandProcessor.processCommand("deposit 12345678 99");
		commandProcessor.processCommand("pass 1");

		Double accountBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(74, accountBalance);

	}

	@Test
	void pass_time_reduces_the_balance_of_checking_acc_by_25_dollars_for_each_month_if_the_balance_is_below_100_from_the_start() {
		commandProcessor.processCommand("create checking 12345678 0");
		commandProcessor.processCommand("deposit 12345678 99");
		commandProcessor.processCommand("pass 3");

		Double accountBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(24, accountBalance);

	}

	@Test
	void pass_time_reduces_the_balance_of_savings_acc_by_25_dollars_after_one_month_if_the_balance_is_below_100_from_the_start() {
		commandProcessor.processCommand("create checking 12345678 0");
		commandProcessor.processCommand("deposit 12345678 99");
		commandProcessor.processCommand("pass 1");

		Double accountBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(74, accountBalance);

	}

	@Test
	void pass_time_reduces_the_balance_of_savings_acc_by_25_dollars_for_each_month_if_the_balance_is_below_100_from_the_start() {
		commandProcessor.processCommand("create savings 12345678 0");
		commandProcessor.processCommand("deposit 12345678 99");
		commandProcessor.processCommand("pass 3");

		Double accountBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(24, accountBalance);

	}

	@Test
	void pass_time_reduces_the_balance_of_checking_acc_by_25_dollars_after_one_month_if_the_balance_gets_under_100_after_some_months_have_already_passed() {
		commandProcessor.processCommand("create checking 12345678 0");
		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("pass 1");
		commandProcessor.processCommand("withdraw 12345678 1");
		commandProcessor.processCommand("pass 1");

		Double accountBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(74, accountBalance);

	}

	@Test
	void pass_time_reduces_the_balance_of_checking_acc_by_25_dollars_for_each_month_if_the_balance_gets_under_100_at_any_point_after_some_months_have_already_passed() {
		commandProcessor.processCommand("create checking 12345678 0");
		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("pass 3");
		commandProcessor.processCommand("withdraw 12345678 1");
		commandProcessor.processCommand("pass 3");

		Double accountBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(24, accountBalance);
	}

	@Test
	void pass_time_reduces_the_balance_of_savings_acc_by_25_dollars_after_one_month_if_the_balance_gets_under_100_after_some_months_have_already_passed() {
		commandProcessor.processCommand("create savings 12345678 0");
		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("pass 1");
		commandProcessor.processCommand("withdraw 12345678 1");
		commandProcessor.processCommand("pass 1");

		Double accountBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(74, accountBalance);

	}

	@Test
	void pass_time_reduces_the_balance_of_savings_acc_by_25_dollars_for_each_month_if_the_balance_gets_under_100_at_any_point_after_some_months_have_already_passed() {
		commandProcessor.processCommand("create savings 12345678 0");
		commandProcessor.processCommand("deposit 12345678 100");
		commandProcessor.processCommand("pass 3");
		commandProcessor.processCommand("withdraw 12345678 1");
		commandProcessor.processCommand("pass 3");

		Double accountBalance = bank.getAccounts().get("12345678").getBalance();

		assertEquals(24, accountBalance);
	}

}
