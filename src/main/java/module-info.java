module com.group55.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.group55.project to javafx.fxml;
    exports com.group55.project;
    exports com.group55.project.ui;
    opens com.group55.project.ui to javafx.fxml;
}