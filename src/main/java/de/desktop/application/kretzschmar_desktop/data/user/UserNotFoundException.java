package de.desktop.application.kretzschmar_desktop.data.user;

public class UserNotFoundException extends UserException {
    private String username;


    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message, String username) {
        super(message);
        this.username = username;
    }

    public UserNotFoundException(String message, String username, User user) {
        super(message, user);
        this.username = username;
    }
}
