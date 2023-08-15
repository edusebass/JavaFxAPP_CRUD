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
    @FXML
    protected void onHelloButtonClick() {
        usuariologin.getText();
        contraseñalogin.getText();
    }

    @FXML
    private void busqueda(String QUERY) {
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);){

            if (rs.next()){
                //Recorre la DB y guarda los datos en los atributos de la clase Persona
                persona.setCodigo(rs.getString("codigo_pers"));
                persona.setCedula(rs.getString("cedula_pers"));

            } else{
                new Alert(Alert.AlertType.ERROR,"Usuario encontrada!").showAndWait();
            }

        } catch(SQLException ex) {
            throw new RuntimeException(ex);

        }
    }
}