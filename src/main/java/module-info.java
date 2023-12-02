module com.a5coding {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.a5coding to javafx.fxml;
    exports com.a5coding.controllers;
    opens com.a5coding.controllers to javafx.fxml;
    exports com.a5coding.io;
    opens com.a5coding.io to javafx.fxml;

}