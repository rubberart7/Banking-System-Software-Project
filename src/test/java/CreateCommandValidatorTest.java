import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void create_checking_acc_has_valid_eight_digit_ID_value() {
		boolean actual = commandValidator.validate("create checking 12345678 3.2");
		assertTrue(actual);
	}

	@Test
	void create_checking_acc_has_invalid_non_eight_digit_ID_value() {
		boolean actual = commandValidator.validate("create checking 1234578 3.2");
		assertFalse(actual);
	}

}
