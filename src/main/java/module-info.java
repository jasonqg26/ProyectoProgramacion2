module com.example.proyectoprogramacion2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens Interface to javafx.fxml;
    exports Interface;
}