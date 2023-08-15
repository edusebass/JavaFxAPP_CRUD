module com.example.demoprueba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demoprueba to javafx.fxml;
    exports com.example.demoprueba;
}