package de.desktop.application.kretzschmar_desktop;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MessageBoxCaller {

    private static MessageBoxController controller;
    private static MessageBoxResult result = controller.result;

    private static Thread messageBoxThread = new Thread(() -> {
        try {
            FXMLLoader loader = new FXMLLoader();
            Scene messageBoxScene = loader.load(Main.class.getResource("messagebox-view.fxml"));
            MessageBoxController _controller = loader.getController();
            controller = _controller;
            Stage messageBoxStage = new Stage();
            messageBoxStage.setScene(messageBoxScene);
            messageBoxStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    });

    /**
     * Call a new MesageBox and wait for the user input.
     * @return The value of the input of the user.
     */
    public static MessageBoxResult openNewMessageBox() {
        Platform.runLater(() -> messageBoxThread.start());
        synchronized (messageBoxThread) {
            while (result == null) {
                try {
                    result.wait();
                } catch (InterruptedException e) { }
            }
        }
        System.out.println("The user input was: " + result);
        return result;
    }
}
