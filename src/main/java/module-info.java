module com.project.stickhero {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.stickhero to javafx.fxml;
    exports com.project.stickhero;
}