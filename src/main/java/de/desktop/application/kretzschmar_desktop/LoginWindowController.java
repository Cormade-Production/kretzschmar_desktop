package de.desktop.application.kretzschmar_desktop;

import de.desktop.application.kretzschmar_desktop.data.user.User;
import de.desktop.application.kretzschmar_desktop.data.user.UserController;
import de.desktop.application.kretzschmar_desktop.data.user.UserNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

public class LoginWindowController {


    class LoginController {
        private User user;

        private boolean checkPassword(String password) {
            boolean passwordLength = false;
            boolean upperCase = false;
            boolean lowerCase = false;

            if(password.length() >= 8) {
                passwordLength = true;
            }

            for(int i = 0; i < password.length(); i++) {
                if(Character.isUpperCase(password.charAt(i))) {
                    upperCase = true;
                }
                if(Character.isLowerCase(password.charAt(i))) {
                    lowerCase = true;
                }
            }

            return passwordLength && upperCase && lowerCase;
        }

        private boolean checkUserInput() {
            if (usernameTextField.getText().length() <= 0) {
                missingUsernameLabel.setVisible(true);
                return false;
            } else if (passwordTextField.getText().length() <= 0) {
                missingPasswordLabel.setVisible(true);
                return false;
            }
            return true;
        }

        private boolean performLogin(String username, String password) {
            User user;
            try {
                user = UserController.getUser(username);
                this.user = user;
            } catch (UserNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return user.getPassword().equals(password);
        }
    }


    //window fields
    @FXML private Pane mainPanel;

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;

    @FXML Label createAccountLabel;
    @FXML Label missingUsernameLabel;
    @FXML Label missingPasswordLabel;

    private boolean loginSuccess = false;


    private final LoginController controller = new LoginController();


    @FXML
    private void initialize() {
        //the event listener for the close request to the window. It will interrupt the gui thread to close the app.
        mainPanel.sceneProperty().addListener((observableValue, oldValue, newValue) ->
                newValue.windowProperty().addListener((observableValue1, oldValue1, newValue1) ->
                        newValue1.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::onWindowClosing)));
    }


    private void onWindowClosing(WindowEvent event) {
        if(!loginSuccess) {
            //call the MessageBox and show the user, that the current thread can´t be processed
        }
    }


    @FXML
    private void createAccountLabelClicked() {
        //redirect the user to register mask
    }

    @FXML
    private void loginButtonClicked() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if(!controller.checkUserInput()) {
            usernameTextField.requestFocus();
            return;
        }
        if(!controller.checkPassword(password)) {
            passwordTextField.requestFocus();
            return;
        }

        if(!controller.performLogin(username, password)) {
            usernameTextField.requestFocus();
            return;
        }

        onLoginSuccess();
    }

    private void onLoginSuccess() {
        //TODO: give the user a message and go back to the main window.
        Main.mainWindow.requestFocus();
        Main.currentUser = controller.user;
    }



    @FXML
    private void cancelButtonClicked() {

    }
}
