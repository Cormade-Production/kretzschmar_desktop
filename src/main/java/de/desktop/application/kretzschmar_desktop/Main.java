package de.desktop.application.kretzschmar_desktop;

import de.desktop.application.kretzschmar_desktop.data.user.User;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Main extends Application {

    public static Window mainWindow;
    public static User currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        ///TODO: open the login window and disable the main window.
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainwindow-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        mainWindow = stage;
        //startUp();
        MessageBoxCaller.openNewMessageBox();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        //TODO: show the user that the application was closed.
    }


    private static void startUp() {
        //open the login window and do all tasks that have to be performed.
        try {
            Parent loginWindow = FXMLLoader.load(Main.class.getResource("loginwindow-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Anmelden");
            stage.setScene(new Scene(loginWindow));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}