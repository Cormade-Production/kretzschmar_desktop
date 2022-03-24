package de.desktop.application.kretzschmar_desktop.data.user;

/**
 * Header exception-class for all exceptions that considers to the user object.
 */
public class UserException extends Exception {
    private User user;

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, User user) {
        super(message);
        this.user = user;
    }

    /**
     * Get the user object, that the exception belongs to or that is caused.
     * @return The user object of the exception.
     */
    public User getUser() {
        return user;
    }
}
