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
		bank.addRegularAccount("12345678", 2.1, "CheckingAccount");
		boolean actual = commandValidator.validate("deposit 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void deposit_more_than_1000_in_checking_acc_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "CheckingAccount");
		boolean actual = commandValidator.validate("deposit 12345678 1001");
		assertFalse(actual);
	}

	@Test
	void deposit_between_0_and_1000_in_checking_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "CheckingAccount");
		boolean actual = commandValidator.validate("deposit 12345678 1");
		assertTrue(actual);
	}

	@Test
	void deposit_0_in_checking_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "CheckingAccount");
		boolean actual = commandValidator.validate("deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	void deposit_more_than_2500_in_savings_is_invalid() {
		bank.addRegularAccount("12345678", 2.1, "SavingsAccount");
		boolean actual = commandValidator.validate("deposit 12345678 2501");
		assertFalse(actual);
	}

	@Test
	void deposit_between_0_and_2500_in_savings_acc_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savingsaccount");
		boolean actual = commandValidator.validate("deposit 12345678 1");
		assertTrue(actual);
	}

	@Test
	void deposit_0_in_savings_is_valid() {
		bank.addRegularAccount("12345678", 2.1, "savingsaccount");
		boolean actual = commandValidator.validate("deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	void deposit_money_into_cd_is_invalid() {
		bank.addCDAccount("12345678", 2.1, 100);
		boolean actual = commandValidator.validate("deposit 12345678 100");
		assertFalse(actual);

	}
}
