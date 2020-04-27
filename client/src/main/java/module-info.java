module com.group5 {
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.media;
    //requires javafx.graphics;
    requires java.desktop;
    requires org.json;
    requires spring.web;

    opens com.group5 to javafx.fxml;
    opens com.group5.controllers to javafx.fxml;
    opens com.group5.models to javafx.base;
    exports com.group5;

}