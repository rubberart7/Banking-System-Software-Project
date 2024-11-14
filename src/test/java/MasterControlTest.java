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
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
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
	void invalid_to_create_accounts_with_same_ID() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checking 12345678 1.0", actual);
	}

	@Test
	void create_part_in_command_is_missing_is_invalid() {
		input.add("checking 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("checking 12345678 1.0", actual);

	}

	@Test
	void account_type_is_missing_in_create_command_is_invalid() {
		input.add("create 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create 12345678 1.0", actual);
	}

	@Test
	void create_command_is_missing_id_value_is_invalid() {
		input.add("create checking 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checking 1.0", actual);
	}

	@Test
	void create_checking_acc_command_with_missing_apr_value_is_invalid() {
		input.add("create checking 12345678");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checking 12345678", actual);
	}

	@Test
	void typo_in_account_type_is_invalid() {
		input.add("create checkig 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checkig 12345678 1.0", actual);
	}

	@Test
	void typo_in_id_value_for_checking_acc_is_invalid() {
		input.add("create checking 123A5678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checking 123A5678 1.0", actual);
	}

	@Test
	void id_value_for_checking_acc_not_being_eight_digits_is_invalid() {
		input.add("create checking 123456789 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checking 123456789 1.0", actual);

	}

	@Test
	void typo_in_id_value_for_savings_acc_is_invalid() {
		input.add("create savings 123A5678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create savings 123A5678 1.0", actual);
	}

	@Test
	void id_value_for_savings_acc_not_being_eight_digits_is_invalid() {
		input.add("create savings 123456789 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create savings 123456789 1.0", actual);

	}

	@Test
	void typo_in_id_value_for_cd_acc_is_invalid() {
		input.add("create cd 123A5678 1.0 1000");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create cd 123A5678 1.0 1000", actual);
	}

	@Test
	void id_value_for_cd_acc_not_being_eight_digits_is_invalid() {
		input.add("create cd 123456789 1.0 1000");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create cd 123456789 1.0 1000", actual);

	}

	@Test
	void create_command_for_checking_has_too_many_arguments_is_invalid() {
		input.add("create checking 12345678 1.0 100");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checking 12345678 1.0 100", actual);

	}

	@Test
	void create_command_for_checking_with_provided_balance_is_invalid() {
		input.add("create checking 12345678 1.0 200");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checking 12345678 1.0 200", actual);

	}

	@Test
	void create_command_for_savings_has_too_many_arguments_is_invalid() {
		input.add("create savings 12345678 1.0 100");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create savings 12345678 1.0 100", actual);

	}

	@Test
	void create_command_for_savings_with_provided_balance_is_invalid() {
		input.add("create savings 12345678 1.0 200");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create savings 12345678 1.0 200", actual);

	}

	@Test
	void create_command_for_cd_has_too_many_arguments_is_invalid() {
		input.add("create cd 12345678 1.0 1000 25");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create cd 12345678 1.0 1000 25", actual);

	}

	@Test
	void deposit_over_1000_in_checking_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.0, "checking");
		input.add("deposit 12345678 1001");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("deposit 12345678 1001", actual);

	}

	@Test
	void deposit_over_2500_in_savings_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.0, "savings");
		input.add("deposit 12345678 2501");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("deposit 12345678 2501", actual);

	}

	@Test
	void deposit_command_has_too_many_arguments_is_invalid() {
		input.add("deposit 12345678 100 200");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("deposit 12345678 100 200", actual);

	}

	@Test
	void deposit_command_has_too_few_arguments_is_invalid() {
		input.add("deposit 12345678");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("deposit 12345678", actual);

	}

	@Test
	void deposit_negative_amount_is_invalid() {
		input.add("deposit 12345678 -1");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("deposit 12345678 -1", actual);

	}

}