module com.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.cincuentazo to javafx.fxml;
    exports com.example.cincuentazo;
}