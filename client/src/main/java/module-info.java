module com.group5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.group5 to javafx.fxml;
    opens com.group5.controllers to javafx.fxml;
    exports com.group5;

}