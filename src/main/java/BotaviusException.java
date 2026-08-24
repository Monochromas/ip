public class BotaviusException extends RuntimeException {

    // Constructor that accepts a custom error message
    public BotaviusException(String message) {
        super(message); // Passes the message to the parent RuntimeException class
    }
}
