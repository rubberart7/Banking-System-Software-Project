import org.junit.jupiter.api.BeforeEach;

public class CommandValidatorTest {
	CreateCommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CreateCommandValidator(bank);

	}
}
