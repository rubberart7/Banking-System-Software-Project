import org.junit.jupiter.api.BeforeEach;

public class DepositCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);

	}

//    cant deposit values outside ranges
//	@Test
//	void deposit_negative_amount_is_invalid() {
//		boolean actual = commandValidator.validate("deposit 12345678 -1");
//		assertFalse(actual);
//	}
//
//	@Test
//	void deposit_more_than_1000_in_checking_acc_is_invalid() {
//		bank.addRegularAccount("12345678", 2.1, "CheckingAccount");
//		boolean actual = commandValidator.validate("deposit 12345678 1001");
//		assertFalse(actual);
//	}
//
//	@Test
//	void deposit_between_0_and_1000_in_checking_acc_is_valid() {
//		bank.addRegularAccount("12345678", 2.1, "CheckingAccount");
//		boolean actual = commandValidator.validate("deposit 12345678 1");
//		assertTrue(actual);
//	}

}
