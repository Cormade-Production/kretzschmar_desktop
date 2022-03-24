module de.desktop.application.kretzschmar_desktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.desktop.application.kretzschmar_desktop to javafx.fxml;
    exports de.desktop.application.kretzschmar_desktop;
}