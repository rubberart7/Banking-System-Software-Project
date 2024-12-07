package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void transfer_some_money_between_0_and_400_from_checking_to_checking_acc_updates_balances_of_both_accs_accordingly() {
		commandProcessor.process("create checking 12345678 3.0");
		commandProcessor.process("create checking 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");

		commandProcessor.process("transfer 12345678 87654321 300");

		assertEquals(700, bank.getAccounts().get("12345678").getBalance());
		assertEquals(300, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void transferring_max_of_400_from_checking_acc_to_another_checking_acc_updates_balance_accordingly() {
		commandProcessor.process("create checking 12345678 3.0");
		commandProcessor.process("create checking 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 400");

		assertEquals(600, bank.getAccounts().get("12345678").getBalance());
		assertEquals(700, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void transferring_0_dollars_from_checking_acc_to_another_checking_acc_makes_sure_the_balance_stays_the_same_for_both_accounts() {
		commandProcessor.process("create checking 12345678 3.0");
		commandProcessor.process("create checking 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 0");

		assertEquals(1000, bank.getAccounts().get("12345678").getBalance());
		assertEquals(300, bank.getAccounts().get("87654321").getBalance());
	}

	@Test
	void transfer_some_money_between_0_and_400_from_checking_to_savings_acc_updates_balances_of_both_accs_accordingly() {
		commandProcessor.process("create checking 12345678 3.0");
		commandProcessor.process("create savings 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");

		commandProcessor.process("transfer 12345678 87654321 300");

		assertEquals(700, bank.getAccounts().get("12345678").getBalance());
		assertEquals(300, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void transferring_max_of_400_from_checking_acc_to_a_savings_acc_updates_balance_accordingly() {
		commandProcessor.process("create checking 12345678 3.0");
		commandProcessor.process("create savings 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 400");

		assertEquals(600, bank.getAccounts().get("12345678").getBalance());
		assertEquals(700, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void transferring_0_dollars_from_checking_acc_to_a_savings_acc_makes_sure_the_balance_stays_the_same_for_both_accounts() {
		commandProcessor.process("create checking 12345678 3.0");
		commandProcessor.process("create savings 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 0");

		assertEquals(1000, bank.getAccounts().get("12345678").getBalance());
		assertEquals(300, bank.getAccounts().get("87654321").getBalance());
	}

	@Test
	void transfer_some_money_between_0_and_1000_from_savings_to_savings_acc_updates_balances_of_both_accs_accordingly() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create savings 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");

		commandProcessor.process("transfer 12345678 87654321 600");

		assertEquals(400, bank.getAccounts().get("12345678").getBalance());
		assertEquals(600, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void transferring_max_of_1000_from_savings_acc_to_another_savings_acc_updates_balance_accordingly() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create savings 87654321 2.0");
		commandProcessor.process("deposit 12345678 1200");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 1000");

		assertEquals(200, bank.getAccounts().get("12345678").getBalance());
		assertEquals(1300, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void transferring_0_dollars_from_savings_acc_to_another_savings_acc_makes_sure_the_balance_stays_the_same_for_both_accounts() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create savings 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 0");

		assertEquals(1000, bank.getAccounts().get("12345678").getBalance());
		assertEquals(300, bank.getAccounts().get("87654321").getBalance());
	}

	@Test
	void transfer_some_money_between_0_and_1000_from_savings_to_checking_acc_updates_balances_of_both_accs_accordingly() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create checking 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");

		commandProcessor.process("transfer 12345678 87654321 600");

		assertEquals(400, bank.getAccounts().get("12345678").getBalance());
		assertEquals(600, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void transferring_max_of_1000_from_savings_acc_to_a_checking_acc_updates_balance_accordingly() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create checking 87654321 2.0");
		commandProcessor.process("deposit 12345678 1200");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 1000");

		assertEquals(200, bank.getAccounts().get("12345678").getBalance());
		assertEquals(1300, bank.getAccounts().get("87654321").getBalance());

	}

	@Test
	void transferring_0_dollars_from_savings_acc_to_a_checking_acc_makes_sure_the_balance_stays_the_same_for_both_accounts() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create checking 87654321 2.0");
		commandProcessor.process("deposit 12345678 1000");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 0");

		assertEquals(1000, bank.getAccounts().get("12345678").getBalance());
		assertEquals(300, bank.getAccounts().get("87654321").getBalance());
	}

	@Test
	void transferring_full_balance_from_checking_to_checking_makes_the_checking_acc_balance_zero() {
		commandProcessor.process("create checking 12345678 3.0");
		commandProcessor.process("create checking 87654321 2.0");
		commandProcessor.process("deposit 12345678 400");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 400");

		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
		assertEquals(700, bank.getAccounts().get("87654321").getBalance());
	}

	@Test
	void transferring_full_balance_from_checking_to_savings_makes_the_checking_acc_balance_zero() {
		commandProcessor.process("create checking 12345678 3.0");
		commandProcessor.process("create savings 87654321 2.0");
		commandProcessor.process("deposit 12345678 400");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 400");

		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
		assertEquals(700, bank.getAccounts().get("87654321").getBalance());
	}

	@Test
	void transferring_full_balance_from_savings_to_savings_makes_the_savings_acc_balance_zero() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create savings 87654321 2.0");
		commandProcessor.process("deposit 12345678 400");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 400");

		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
		assertEquals(700, bank.getAccounts().get("87654321").getBalance());
	}

	@Test
	void transferring_full_balance_from_savings_to_checking_makes_the_savings_acc_balance_zero() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create checking 87654321 2.0");
		commandProcessor.process("deposit 12345678 400");
		commandProcessor.process("deposit 87654321 300");

		commandProcessor.process("transfer 12345678 87654321 400");

		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
		assertEquals(700, bank.getAccounts().get("87654321").getBalance());
	}

}
