module com.project.stickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens project.stickhero to javafx.fxml;
    exports project.stickhero.Animation;
    opens project.stickhero.Animation to javafx.fxml;
    exports project.stickhero.Backend;
    opens project.stickhero.Backend to javafx.fxml;
}