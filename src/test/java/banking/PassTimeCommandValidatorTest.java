package banking;

import org.junit.jupiter.api.BeforeEach;

public class PassTimeCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);

	}

}
