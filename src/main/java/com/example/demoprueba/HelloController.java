package com.example.demoprueba;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;

public class HelloController {
    @FXML
    private TextField usuariologin;
    @FXML
    private TextField contraseñalogin;

    //Variables conexion MySQL
    final static String DB_URL="jdbc:mysql://localhost/sistemalogin";
    final static String USER="root";
    final static String PASS="edu1751395623";
/////////
    public String usuario;
    public String password;

    @FXML
    protected void onHelloButtonClick() {
        usuario = usuariologin.getText();
        password = contraseñalogin.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String QUERY = "SELECT nombrebd FROM usuariosbd WHERE nombrebd = ? AND passwordbd = ?";
            PreparedStatement stmt = conn.prepareStatement(QUERY);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                new Alert(Alert.AlertType.ERROR,"Persona encontrada!").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR,"Persona no encontrada!").showAndWait();
            }
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }
}