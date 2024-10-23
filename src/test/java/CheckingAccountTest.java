import org.junit.jupiter.api.BeforeEach;

public class CheckingAccountTest {
	CheckingAccount account;

	@BeforeEach
	public void setUp() {
		account = new CheckingAccount("12345678", 9.0);
	}

}
