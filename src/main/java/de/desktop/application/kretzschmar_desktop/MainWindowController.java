package de.desktop.application.kretzschmar_desktop;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Manage the interaction of the mainWindow with the backend. It provides all methods for the components of
 * the window. It also initializes the window and all the components.
 * Also provides inner classes for thread to do task in the background while running.
 */
public class MainWindowController {
    /**
     * Update the gui each second.
     * It will update the timer on the screen and also the labels of the window.
     */
    class GuiAdjustmentThread extends Thread {
        boolean running = false;

        @Override
        public void run() {
            running = true;
            while (running) {
                updateTime();
                updateGreeting();
                updateTaskGreeting();
                updateDateLabel();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) { } //do nothing
            }
         }

        @Override
        public void interrupt() {
            super.interrupt();
            running = false;
        }
    }



    //window fields
    @FXML private Pane mainPanel;

    @FXML private Label greetingLabel;
    @FXML private Label usernameLabel;
    @FXML private Label timeLabel;
    @FXML private Label taskGreetingLabel;
    @FXML private Label dateLabel;

    private Window window;


    //class fields
    Thread guiAdjustmentThread = new GuiAdjustmentThread();

    @FXML
    private void initialize() {
        //the event listener for the close request to the window. It will interrupt the gui thread to close the app.
        mainPanel.sceneProperty().addListener((observableValue, oldValue, newValue) ->
                newValue.windowProperty().addListener((observableValue1, oldValue1, newValue1) ->
                        newValue1.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> onWindowClosing())));

        //start the threads of the class
        guiAdjustmentThread.start();
    }

    private void onWindowClosing() {
        //perform the task, that should be done when the window is closing
        guiAdjustmentThread.interrupt();
    }


    //get the current time
    private String getCurrentTime() {
        return DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now());
    }

    private void updateTime() {
        Platform.runLater(() -> timeLabel.setText(getCurrentTime()));
    }


    private enum TimeGreetings {
        Morning,
        Midday,
        Afternoon,
        Night
    }

    //get the greeting by the time enum
    private TimeGreetings getGreeting(String time) {
        String hour = time.substring(0, 2);
        return switch (hour) {
            case "06", "07", "08", "09", "10" -> TimeGreetings.Morning;
            case "11", "12", "13", "14", "15" -> TimeGreetings.Midday;
            case "16", "17", "18", "20", "21" -> TimeGreetings.Afternoon;
            default -> TimeGreetings.Night;
        };
    }

    //get the written greeting by the enum
    private String getGreetingString(TimeGreetings timeGreetings) {
        switch (timeGreetings) {
            case Morning -> {
                greetingLabel.setFont(new Font("Arial", 96));
                return "Guten Morgen";
            }
            case Midday -> {
                greetingLabel.setFont(new Font("Arial", 51));
                return "Guten Mittag und guten Hunger :)";
            }
            case Afternoon -> {
                greetingLabel.setFont(new Font("Arial", 90));
                return "Oh rieche ich hier Kaffee?";
            }
        }

        greetingLabel.setFont(new Font("Arial", 51));
        return "Ich glaube es wird Zeit zu schlafen!";
    }

    private void updateGreeting() {
        Platform.runLater(() -> greetingLabel.setText(getGreetingString(getGreeting(timeLabel.getText()))));
    }


    private enum TaskCollection {
        Nothing,
        Todo,
        NextDay
    }

    private TaskCollection getTaskState() {
        //TODO: check if there are open task at the day.
        return TaskCollection.Nothing;
    }

    private String getTaskString(TaskCollection collection) {
        return switch (collection) {
            case NextDay -> "Für heute bist du durch aber denk an morgen!";
            case Todo -> "Hoffentlich hast du dir noch Zeit eingeplant!";
            default -> "Heute steht nichts mehr an! Leg doch die Beine hoch!";
        };
    }

    private void updateTaskGreeting() {
        Platform.runLater(() -> taskGreetingLabel.setText(getTaskString(getTaskState())));
    }

    private void updateDateLabel() {
        Platform.runLater(() -> dateLabel.setText(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now())));
    }
}