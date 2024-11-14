import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	private final List<String> invalidCommands = new ArrayList<>();

	protected void addInvalidCommand(String command) {
		invalidCommands.add(command);

	}

	protected List<String> getInvalidCommands() {
		return invalidCommands;
	}
}
