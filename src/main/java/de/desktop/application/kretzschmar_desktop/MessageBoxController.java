package de.desktop.application.kretzschmar_desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MessageBoxController {

    public MessageBoxResult result;

    @FXML private Pane mainPanel;

    @FXML
    private void initialize() {
        Window window = mainPanel.getScene().getWindow();
        Stage stage = (Stage) window;
        stage.setAlwaysOnTop(true);
        for(Window applicationWindow : Window.getWindows()) {
            try {
                Window _window = FXMLLoader.load(Main.class.getResource("messagebox-view.fxml"));
                if (applicationWindow != _window) {
                    //make the window disabled
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    private void onOkButtonClicked() {
        result = MessageBoxResult.OKButton;
    }


    @FXML
    private void onCancelButtonClicked() {
        result = MessageBoxResult.CancelButton;
    }
}
