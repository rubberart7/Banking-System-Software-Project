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
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);

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
	void typo_in_create_command_and_deposit_command_makes_sure_to_add_those_invalid_commands_to_the_end_of_the_output_list() {
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

}