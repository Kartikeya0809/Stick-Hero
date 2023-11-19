module com.project.stickhero {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.stickhero to javafx.fxml;
    exports project.stickhero;
}