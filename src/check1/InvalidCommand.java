package check1;

public class InvalidCommand extends Throwable {
    public InvalidCommand(String message) {
        super(message);
    }
}
