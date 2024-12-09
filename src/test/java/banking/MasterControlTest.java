package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	Bank bank;
	List<String> input;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank));
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

// create command
	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);

	}

	@Test
	void decimals_are_made_sure_to_round_down() {
		input.add("create savings 12345678 2.198");
		input.add("deposit 12345678 1000.279");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 1000.27 2.19", actual.get(0));
		assertEquals("deposit 12345678 1000.279", actual.get(1));

	}

	@Test
	void transfer_with_one_acc_not_existing_is_invalid() {
		input.add("create checking 12345678 10");
		input.add("transfer 123455678 98765432 100");
		input.add("deposit 12345678 100");
		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 100.00 10.00", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
		assertEquals("transfer 123455678 98765432 100", actual.get(2));

	}

	@Test
	void typo_in_pass_command_is_invalid() {
		input.add("pas 16");
		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("pas 16", actual.get(0));
	}

	@Test
	void typo_in_transfer_command_is_invalid() {
		input.add("transfe 12345678 87654321 100");
		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("transfe 12345678 87654321 100", actual.get(0));
	}

	@Test
	void create_savings_acc_with_typo_in_savings_name_is_invalid() {
		input.add("create saving 12345678 2.1");
		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("create saving 12345678 2.1", actual.get(0));
	}

	@Test
	void create_checking_acc_with_typo_in_checking_name_is_invalid() {
		input.add("create checkin 12345678 2.1");
		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("create checkin 12345678 2.1", actual.get(0));
	}

	@Test
	void create_cd_acc_with_typo_in_cd_name_is_invalid() {
		input.add("create cdd 12345678 2.1 1000");
		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("create cdd 12345678 2.1 1000", actual.get(0));
	}

	@Test
	void deposit_into_a_cd_acc_makes_the_command_invalid() {
		input.add("create cd 12345678 7.5 1000");
		input.add("deposit 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Cd 12345678 1000.00 7.50", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));

	}

	@Test
	void create_cd_without_starting_balance_is_invalid() {
		input.add("create cd 12345678 7.5");
		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("create cd 12345678 7.5", actual.get(0));
	}

	@Test
	void invalid_to_use_a_create_command_without_valid_eight_digit_numeric_value() {
		input.add("create checking 876543A1 2.1");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("create checking 876543A1 2.1", actual.get(0));
	}

	@Test
	void invalid_create_command_with_invalid_id_adds_it_to_the_end() {
		input.add("create checking 12345678 3.0");
		input.add("create checking 1234A5678 2.0");
		input.add("deposit 12345678 1000");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 1000.00 3.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("create checking 1234A5678 2.0", actual.get(2));

	}

	@Test
	void two_invalid_create_command_with_invalid_id_adds_it_to_the_end() {
		input.add("create checking 12345678 3.0");
		input.add("create checking 1234A5678 2.0");
		input.add("create checking 1234A5678 2.0");
		input.add("deposit 12345678 1000");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 1000.00 3.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("create checking 1234A5678 2.0", actual.get(2));
		assertEquals("create checking 1234A5678 2.0", actual.get(3));

	}

	@Test
	void invalid_deposit_command_with_invalid_id_adds_it_to_the_end() {
		input.add("create checking 12345678 3.0");
		input.add("deposit 1234A678 1000");
		input.add("create checking 87654321 2.0");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 0.00 3.00", actual.get(0));
		assertEquals("Checking 87654321 0.00 2.00", actual.get(1));
		assertEquals("deposit 1234A678 1000", actual.get(2));

	}

	@Test
	void two_invalid_deposit_command_with_invalid_id_adds_it_to_the_end() {
		input.add("create checking 12345678 3.0");
		input.add("deposit 1234A678 1000");
		input.add("deposit 12344678 1001");
		input.add("create checking 87654321 2.0");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 0.00 3.00", actual.get(0));
		assertEquals("Checking 87654321 0.00 2.00", actual.get(1));
		assertEquals("deposit 1234A678 1000", actual.get(2));
		assertEquals("deposit 12344678 1001", actual.get(3));

	}

	@Test
	void invalid_withdraw_command_with_invalid_id_adds_it_to_the_end() {
		input.add("create checking 12345678 3.0");
		input.add("withdraw 1234A678 1000");
		input.add("create checking 87654321 2.0");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 0.00 3.00", actual.get(0));
		assertEquals("Checking 87654321 0.00 2.00", actual.get(1));
		assertEquals("withdraw 1234A678 1000", actual.get(2));

	}

	@Test
	void two_invalid_withdraw_command_with_invalid_id_adds_it_to_the_end() {
		input.add("create checking 12345678 3.0");
		input.add("withdraw 1234A678 1000");
		input.add("create checking 87654321 2.0");
		input.add("create checking 98765432 1.0");
		input.add("withdraw 1234A678 1200");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Checking 12345678 0.00 3.00", actual.get(0));
		assertEquals("Checking 87654321 0.00 2.00", actual.get(1));
		assertEquals("Checking 98765432 0.00 1.00", actual.get(2));
		assertEquals("withdraw 1234A678 1000", actual.get(3));
		assertEquals("withdraw 1234A678 1200", actual.get(4));

	}

	@Test
	void trying_to_withdraw_from_an_acc_that_does_not_exist_makes_the_command_invalid_and_adds_to_the_end_of_the_list() {
		input.add("create checking 12345678 2.1");
		input.add("withdraw 87654321 100");
		input.add("deposit 12345678 100");
		input.add("create savings 87654321 3.1");
		input.add("deposit 87654321 200");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Checking 12345678 100.00 2.10", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
		assertEquals("Savings 87654321 200.00 3.10", actual.get(2));
		assertEquals("deposit 87654321 200", actual.get(3));
		assertEquals("withdraw 87654321 100", actual.get(4));

	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 12345678 100", actual);
	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
	}

	@Test
	void make_sure_that_output_list_has_account_status_for_one_account() {
		input.add("Create savings 12345678 0.6");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));

	}

	@Test
	void make_sure_that_output_list_has_account_status_for_multiple_accounts_in_order() {
		input.add("Create savings 12345678 0.6");

		input.add("Create savings 87654321 3.0");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
		assertEquals("Savings 87654321 0.00 3.00", actual.get(1));

	}

	@Test
	void typo_in_create_command_makes_sure_to_add_that_invalid_command_to_the_end_of_the_output_list() {
		input.add("Create savings 12345678 0");
		input.add("Deposit 12345678 700");

		input.add("createe checking 98765432 0");

		input.add("create checking 87654321 0");
		input.add("transfer 12345678 87654321 400");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 300.00 0.00", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("transfer 12345678 87654321 400", actual.get(2));
		assertEquals("Checking 87654321 400.00 0.00", actual.get(3));
		assertEquals("transfer 12345678 87654321 400", actual.get(4));
		assertEquals("createe checking 98765432 0", actual.get(5));

	}

	@Test
	void typo_in_create_command_and_invalid_deposit_command_makes_sure_to_add_those_invalid_commands_to_the_end_of_the_output_list() {
		input.add("Create savings 12345678 0");
		input.add("Deposit 12345678 700");

		input.add("createe checking 98765432 0");
		input.add("deposit 12345678 2501");

		input.add("create checking 87654321 0");
		input.add("transfer 12345678 87654321 400");
		List<String> actual = masterControl.start(input);

		assertEquals(7, actual.size());
		assertEquals("Savings 12345678 300.00 0.00", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("transfer 12345678 87654321 400", actual.get(2));
		assertEquals("Checking 87654321 400.00 0.00", actual.get(3));
		assertEquals("transfer 12345678 87654321 400", actual.get(4));
		assertEquals("createe checking 98765432 0", actual.get(5));
		assertEquals("deposit 12345678 2501", actual.get(6));

	}

	@Test
	void trying_to_create_an_acc_with_the_same_id_adds_the_invalid_command_to_end_of_output_list() {
		input.add("create checking 12345678 3.0");
		input.add("create savings 12345678 1.0");
		input.add("create checking 87654321 1.0");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 0.00 3.00", actual.get(0));
		assertEquals("Checking 87654321 0.00 1.00", actual.get(1));
		assertEquals("create savings 12345678 1.0", actual.get(2));

	}

	@Test
	void trying_to_create_an_acc_with_typos_adds_the_invalid_commands_to_end_of_output_list() {
		input.add("create checking 12345678 3.0");
		input.add("createe savings 12345679 1.0");
		input.add("crate savings 98765432 1.0");
		input.add("create checking 87654321 1.0");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 0.00 3.00", actual.get(0));
		assertEquals("Checking 87654321 0.00 1.00", actual.get(1));
		assertEquals("createe savings 12345679 1.0", actual.get(2));
		assertEquals("crate savings 98765432 1.0", actual.get(3));
	}

// pass time checks
	@Test
	void pass_time_of_a_few_months_makes_the_account_disappear_and_makes_it_and_its_transactional_methods_not_appear_in_output_list() {
		input.add("create checking 12345678 0");
		input.add("deposit 12345678 75");
		input.add("create savings 98765432 0");
		input.add("deposit 98765432 100");
		input.add("pass 4");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 98765432 100.00 0.00", actual.get(0));
		assertEquals("deposit 98765432 100", actual.get(1));

	}

	@Test
	void pass_time_a_few_months_does_not_make_the_checking_acc_disappear_if_the_balance_is_higher_than_100() {
		input.add("create checking 12345678 0");
		input.add("deposit 12345678 100");
		input.add("deposit 12345678 110");
		input.add("withdraw 12345678 110");
		input.add("pass 3");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 100.00 0.00", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
		assertEquals("deposit 12345678 110", actual.get(2));
		assertEquals("withdraw 12345678 110", actual.get(3));
	}

	@Test
	void pass_time_a_few_months_does_not_make_the_savings_acc_disappear_if_the_balance_is_higher_than_100() {
		input.add("create savings 12345678 0");
		input.add("deposit 12345678 100");
		input.add("deposit 12345678 110");
		input.add("withdraw 12345678 110");
		input.add("pass 3");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 100.00 0.00", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
		assertEquals("deposit 12345678 110", actual.get(2));
		assertEquals("withdraw 12345678 110", actual.get(3));
	}

// withdraw checks
	@Test
	void withdraw_reflects_the_new_balance_of_the_account_after_withdrawal_happens() {
		input.add("create checking 12345678 0");
		input.add("deposit 12345678 75");
		input.add("create savings 98765432 0");
		input.add("deposit 98765432 200");
		input.add("pass 4");
		input.add("withdraw 98765432 100");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 98765432 100.00 0.00", actual.get(0));
		assertEquals("deposit 98765432 200", actual.get(1));
		assertEquals("withdraw 98765432 100", actual.get(2));

	}

	@Test
	void withdrawing_from_a_cd_acc_and_passing_time_causes_its_info_to_update() {
		input.add("create checking 12345678 0");
		input.add("deposit 12345678 100");
		input.add("create cd 87654321 0 1000");
		input.add("Pass 12");
		input.add("withdraw 87654321 1000");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 100.00 0.00", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
		assertEquals("Cd 87654321 0.00 0.00", actual.get(2));
		assertEquals("withdraw 87654321 1000", actual.get(3));

	}

	@Test
	void withdrawing_from_a_cd_acc_and_passing_time_after_causes_it_to_disappear() {
		input.add("create checking 12345678 0");
		input.add("deposit 12345678 100");
		input.add("create cd 87654321 0 1000");
		input.add("Pass 12");
		input.add("withdraw 87654321 1000");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 100.00 0.00", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));

	}

	@Test
	void withdrawing_from_a_cd_acc_and_not_passing_time_causes_its_info_to_remain_and_for_the_withdrawal_to_be_invalid() {
		input.add("create checking 12345678 0");
		input.add("deposit 12345678 100");
		input.add("create cd 87654321 0 1000");
		input.add("withdraw 87654321 1000");
		input.add("deposit 12345678 200");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Checking 12345678 300.00 0.00", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
		assertEquals("deposit 12345678 200", actual.get(2));
		assertEquals("Cd 87654321 1000.00 0.00", actual.get(3));
		assertEquals("withdraw 87654321 1000", actual.get(4));

	}

	@Test
	void withdraw_updates_the_acc_info_for_checking_acc_accordingly() {
		input.add("create checking 12345678 2.0");
		input.add("deposit 12345678 1000");
		input.add("withdraw 12345678 400");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 600.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("withdraw 12345678 400", actual.get(2));

	}

	@Test
	void withdraw_updates_the_acc_info_for_savings_acc_accordingly() {
		input.add("create savings 12345678 2.0");
		input.add("deposit 12345678 2500");
		input.add("withdraw 12345678 1000");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 1500.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 2500", actual.get(1));
		assertEquals("withdraw 12345678 1000", actual.get(2));

	}

	@Test
	void invalid_withdraw_doesnt_update_the_acc_info_for_checking_acc_accordingly() {
		input.add("create checking 12345678 2.0");
		input.add("deposit 12345678 1000");
		input.add("withdraw 12345678 401");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 1000.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("withdraw 12345678 401", actual.get(2));

	}

	@Test
	void invalid_withdraw_doesent_update_the_acc_info_for_savings_acc_accordingly() {
		input.add("create savings 12345678 2.0");
		input.add("deposit 12345678 2500");
		input.add("withdraw 12345678 1001");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 2500.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 2500", actual.get(1));
		assertEquals("withdraw 12345678 1001", actual.get(2));

	}

//	transfer checks

	@Test
	void valid_transfer_updates_each_acc_info_accordingly_for_checking_and_checking_and_displays_in_correct_order() {
		input.add("create checking 12345678 2.0");
		input.add("create checking 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 400");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Checking 12345678 600.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("transfer 12345678 87654321 400", actual.get(2));
		assertEquals("Checking 87654321 1200.00 2.00", actual.get(3));
		assertEquals("deposit 87654321 800", actual.get(4));
		assertEquals("transfer 12345678 87654321 400", actual.get(5));

	}

	@Test
	void valid_transfer_updates_each_acc_info_accordingly_for_checking_and_savings_and_displays_in_correct_order() {
		input.add("create checking 12345678 2.0");
		input.add("create savings 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 400");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Checking 12345678 600.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("transfer 12345678 87654321 400", actual.get(2));
		assertEquals("Savings 87654321 1200.00 2.00", actual.get(3));
		assertEquals("deposit 87654321 800", actual.get(4));
		assertEquals("transfer 12345678 87654321 400", actual.get(5));

	}

	@Test
	void valid_transfer_updates_each_acc_info_accordingly_for_savings_and_savings_and_displays_in_correct_order() {
		input.add("create savings 12345678 2.0");
		input.add("create savings 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 400");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 600.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("transfer 12345678 87654321 400", actual.get(2));
		assertEquals("Savings 87654321 1200.00 2.00", actual.get(3));
		assertEquals("deposit 87654321 800", actual.get(4));
		assertEquals("transfer 12345678 87654321 400", actual.get(5));

	}

	@Test
	void valid_transfer_updates_each_acc_info_accordingly_for_savings_and_checking_and_displays_in_correct_order() {
		input.add("create savings 12345678 2.0");
		input.add("create checking 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 400");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 600.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("transfer 12345678 87654321 400", actual.get(2));
		assertEquals("Checking 87654321 1200.00 2.00", actual.get(3));
		assertEquals("deposit 87654321 800", actual.get(4));
		assertEquals("transfer 12345678 87654321 400", actual.get(5));

	}

	@Test
	void invalid_transfer_with_checking_and_checking_does_not_change_acc_info_and_puts_invalid_command_at_the_end() {
		input.add("create checking 12345678 2.0");
		input.add("create checking 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 401");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Checking 12345678 1000.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("Checking 87654321 800.00 2.00", actual.get(2));
		assertEquals("deposit 87654321 800", actual.get(3));
		assertEquals("transfer 12345678 87654321 401", actual.get(4));

	}

	@Test
	void invalid_transfer_with_checking_and_savings_does_not_change_acc_info_and_puts_invalid_command_at_the_end() {
		input.add("create checking 12345678 2.0");
		input.add("create savings 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 401");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Checking 12345678 1000.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("Savings 87654321 800.00 2.00", actual.get(2));
		assertEquals("deposit 87654321 800", actual.get(3));
		assertEquals("transfer 12345678 87654321 401", actual.get(4));

	}

	@Test
	void invalid_transfer_with_savings_and_checking_does_not_change_acc_info_and_puts_invalid_command_at_the_end() {
		input.add("create savings 12345678 2.0");
		input.add("create checking 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 1001");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("Checking 87654321 800.00 2.00", actual.get(2));
		assertEquals("deposit 87654321 800", actual.get(3));
		assertEquals("transfer 12345678 87654321 1001", actual.get(4));

	}

	@Test
	void invalid_transfer_with_savings_and_savings_does_not_change_acc_info_and_puts_invalid_command_at_the_end() {
		input.add("create savings 12345678 2.0");
		input.add("create savings 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 1001");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("Savings 87654321 800.00 2.00", actual.get(2));
		assertEquals("deposit 87654321 800", actual.get(3));
		assertEquals("transfer 12345678 87654321 1001", actual.get(4));

	}

	@Test
	void another_invalid_transfer_with_savings_and_savings_does_not_change_acc_info_and_puts_invalid_command_at_the_end() {
		input.add("create savings 12345678 2.0");
		input.add("create savings 87654321 2.0");

		input.add("deposit 12345678 1000");
		input.add("deposit 87654321 800");

		input.add("transfer 12345678 87654321 1001");

		input.add("deposit 87654321 400");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 1000.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("Savings 87654321 1200.00 2.00", actual.get(2));
		assertEquals("deposit 87654321 800", actual.get(3));
		assertEquals("deposit 87654321 400", actual.get(4));
		assertEquals("transfer 12345678 87654321 1001", actual.get(5));

	}

//	APR calculations

	@Test
	void apr_calc_shows_updated_values_with_correct_decimal_points_for_checking_acc_after_pass_time() {
		input.add("create checking 12345678 3");
		input.add("deposit 12345678 1000");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 1002.50 3.00", actual.get(0));

	}

	@Test
	void apr_calc_shows_updated_values_with_correct_decimal_points_for_savings_acc_after_pass_time() {
		input.add("create savings 12345678 0.6");
		input.add("deposit 12345678 2500");
		input.add("deposit 12345678 2500");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 5002.50 0.60", actual.get(0));
		assertEquals("deposit 12345678 2500", actual.get(1));
		assertEquals("deposit 12345678 2500", actual.get(2));

	}

	@Test
	void another_apr_calc_shows_updated_values_with_correct_decimal_points_for_checking_acc_after_pass_time() {
		input.add("create checking 12345678 0.6");
		input.add("deposit 12345678 1000");
		input.add("deposit 12345678 1000");
		input.add("deposit 12345678 1000");
		input.add("deposit 12345678 1000");
		input.add("deposit 12345678 1000");

		input.add("pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Checking 12345678 5002.50 0.60", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("deposit 12345678 1000", actual.get(2));
		assertEquals("deposit 12345678 1000", actual.get(3));
		assertEquals("deposit 12345678 1000", actual.get(4));
	}

	@Test
	void apr_calc_for_cd_acc_shows_updated_values_with_correct_decimal_points_after_pass_time() {
		input.add("create cd 12345678 2.1 2000");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Cd 12345678 2014.03 2.10", actual.get(0));
	}

	@Test
	void test_for_all_valid_values_with_multiple_different_command_types() {
		input.add("create checking 12345678 0");
		input.add("deposit 12345678 1000");
		input.add("create savings 87654321 0");
		input.add("deposit 87654321 2500");

		input.add("withdraw 12345678 400");
		input.add("withdraw 87654321 1000");

		input.add("transfer 12345678 87654321 400");
		List<String> actual = masterControl.start(input);

		assertEquals(8, actual.size());
		assertEquals("Checking 12345678 200.00 0.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("withdraw 12345678 400", actual.get(2));
		assertEquals("transfer 12345678 87654321 400", actual.get(3));
		assertEquals("Savings 87654321 1900.00 0.00", actual.get(4));
		assertEquals("deposit 87654321 2500", actual.get(5));
		assertEquals("withdraw 87654321 1000", actual.get(6));
		assertEquals("transfer 12345678 87654321 400", actual.get(7));

	}

	@Test
	void another_test_for_all_valid_values_with_multiple_different_command_types() {
		input.add("create checking 12345678 0");
		input.add("deposit 12345678 1000");
		input.add("create savings 87654321 0");
		input.add("deposit 87654321 2500");

		input.add("withdraw 12345678 400");
		input.add("withdraw 87654321 1000");

		input.add("transfer 12345678 87654321 400");
		input.add("withdraw 12345678 150");
		input.add("create cd 98765432 0 10000");
		input.add("pass 3");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 87654321 1900.00 0.00", actual.get(0));
		assertEquals("deposit 87654321 2500", actual.get(1));
		assertEquals("withdraw 87654321 1000", actual.get(2));
		assertEquals("transfer 12345678 87654321 400", actual.get(3));
		assertEquals("Cd 98765432 10000.00 0.00", actual.get(4));

	}

	@Test
	void can_create_another_acc_with_the_same_id_of_an_old_closed_acc() {
		input.add("create checking 12345678 2.0");
		input.add("pass 1");
		input.add("create checking 12345678 0.6");
		input.add("DePoSiT 12345678 1000");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 1000.50 0.60", actual.get(0));
		assertEquals("DePoSiT 12345678 1000", actual.get(1));
	}

	@Test
	void withdraw_twice_in_same_month_for_savings_makes_command_invalid_and_puts_it_to_the_end() {
		input.add("create savings 12345678 0.6");
		input.add("deposit 12345678 1300");
		input.add("create checking 87654321 0");
		input.add("transfer 12345678 87654321 300");
		input.add("withdraw 12345678 100");
		input.add("pass 1");
		input.add("create cd 98765432 6.1 1000");

		List<String> actual = masterControl.start(input);

		assertEquals(7, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("deposit 12345678 1300", actual.get(1));
		assertEquals("transfer 12345678 87654321 300", actual.get(2));
		assertEquals("Checking 87654321 300.00 0.00", actual.get(3));
		assertEquals("transfer 12345678 87654321 300", actual.get(4));
		assertEquals("Cd 98765432 1000.00 6.10", actual.get(5));
		assertEquals("withdraw 12345678 100", actual.get(6));

	}

	@Test
	void withdraw_in_different_months_for_savings_makes_it_valid() {
		input.add("create checking 12345678 0.0");
		input.add("depOsiT 12345678 1000");

		input.add("wiThdRaw 12345678 100");
		input.add("pass 1");
		input.add("wIthDraW 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 800.00 0.00", actual.get(0));
		assertEquals("depOsiT 12345678 1000", actual.get(1));
		assertEquals("wiThdRaw 12345678 100", actual.get(2));
		assertEquals("wIthDraW 12345678 100", actual.get(3));
	}

}