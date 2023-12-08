module com.project.stickhero {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.stickhero to javafx.fxml;
    exports project.stickhero.backend;
    opens project.stickhero.backend to javafx.fxml;
    exports project.stickhero.Animation;
    opens project.stickhero.Animation to javafx.fxml;
}