package de.desktop.application.kretzschmar_desktop.data.user;

public class UsernameTakenException extends UserException {
    private String username;

    public UsernameTakenException() {
        super("This username is already taken!");
    }

    public UsernameTakenException(User user) {
        super("This username is already taken!");
        this.username = user.getUsername();
    }

    public UsernameTakenException(String message) {
        super(message);
    }

    public UsernameTakenException(String message, String username) {
        super(message);
        this.username = username;
    }

    public UsernameTakenException(String message, String username, User user) { super(message, user); }


    public String getUsername() {
        return username;
    }
}
