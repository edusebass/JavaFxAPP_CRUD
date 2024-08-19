package com.example.javafx1;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.sql.*;

public class AppController {
    @FXML
    private ComboBox<String> signoSelect;
    @FXML
    private Label encabezado, codigo_pers, cedula_pers, nombre_pers, fechaNac_pers, signoZod_pers, formato_fecha;
    @FXML
    private TextField codigo_in, cedula_in, nombre_in, fechaNac_in;

    //Objeto para la clase Persona
    private Persona persona = new Persona();

    @FXML
    private void initialize(){
        //Estilo de letra
        encabezado.setFont(Font.font("System", FontWeight.BOLD,14));
        codigo_pers.setFont(Font.font("System", FontWeight.BOLD,12));
        cedula_pers.setFont(Font.font("System", FontWeight.BOLD,12));
        nombre_pers.setFont(Font.font("System", FontWeight.BOLD,12));
        fechaNac_pers.setFont(Font.font("System", FontWeight.BOLD,12));
        signoZod_pers.setFont(Font.font("System", FontWeight.BOLD,12));
        formato_fecha.setFont(Font.font("System", FontPosture.ITALIC,11));
        //Items del combo box
        signoSelect.setItems(FXCollections.observableArrayList("Acuario","Piscis","Aries","Tauro","Geminis","Cancer","Leo","Virgo","Libra","Escorpio","Sagitario","Capricornio"));
    }

    //Conexion con MySQL
    final static String DB_URL="jdbc:mysql://localhost/registropersonas";
    final static String USER="root";
    final static String PASS="edu1751395623";

    //Boton de busqueda por codigo
    @FXML
    private void buscarPorCodigo(){
        if (codigo_in.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING,"Llena el campo para iniciar la búsqueda!").showAndWait();
        } else {
            String QUERY = "SELECT * from personas where codigo_pers='"+codigo_in.getText()+"'";
            buscarPersona(QUERY);
        }
    }

    //Boton de busqueda por nombre
    @FXML
    private void buscarPorNombre(){
        if(nombre_in.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Llena el campo para iniciar la búsqueda!").showAndWait();
        } else{
            String QUERY = "SELECT * from personas where nombre_pers='"+nombre_in.getText()+"'";
            buscarPersona(QUERY);
        }
    }

    //Boton para borrar el registro
    @FXML
    private void borrarRegistro(){
        //new Alert(Alert.AlertType.CONFIRMATION,"¿Estás seguro de borrar el registro?").showAndWait();
        ButtonType result = new Alert(Alert.AlertType.CONFIRMATION,"¿Estás seguro de borrar el registro?").showAndWait().orElse(ButtonType.CANCEL);
        if(result == ButtonType.OK){
            String deleteQuery = "DELETE FROM personas WHERE codigo_pers="+codigo_in.getText();
            modificarRegistro(deleteQuery);
            new Alert(Alert.AlertType.INFORMATION,"Registro borrado exitosamente!").showAndWait();
        } else{
            new Alert(Alert.AlertType.INFORMATION,"Borrado cancelado!").showAndWait();
        }
    }

    //Boton para actualizar el presente registro
    @FXML
    private void actualizarRegistro(){
        String nombre = nombre_in.getText();
        String fechaNac = fechaNac_in.getText();
        String signoZod = signoSelect.getValue();

        ButtonType result = new Alert(Alert.AlertType.CONFIRMATION,"¿Estás seguro de actualizar el registro?").showAndWait().orElse(ButtonType.CANCEL);
        if(result == ButtonType.OK){
            String updateQuery = "UPDATE personas SET nombre_pers='"+nombre+"',fechaNac_pers='"+fechaNac+"',signoZod_pers='"+signoZod+"'WHERE codigo_pers='"+codigo_in.getText()+"'";
            modificarRegistro(updateQuery);
            new Alert(Alert.AlertType.INFORMATION,"Registro actualizado exitosamente!").showAndWait();
        } else{
            new Alert(Alert.AlertType.INFORMATION,"Actualización cancelada!").showAndWait();
        }
    }

    //Boton para ingresar el presente registro
    @FXML
    private void ingresarRegistro(){
        String codigo = codigo_in.getText();
        String cedula = cedula_in.getText();
        String nombre = nombre_in.getText();
        String fechaNac = fechaNac_in.getText();
        String signoZod = signoSelect.getValue();

        //Validacion por campos vacios
        if(codigo.isEmpty() || cedula.isEmpty() || nombre.isEmpty() || fechaNac.isEmpty() || signoZod == null){
            new Alert(Alert.AlertType.WARNING,"Por favor, llena todos los campos para completar el registro").showAndWait();
        } else {
            Persona nuevaPersona = new Persona(codigo,cedula,nombre,fechaNac,signoZod);
            insertPersona(nuevaPersona);
        }
    }

    //Boton para limpiar los campos del registro
    @FXML
    private void limpiarRegistro(){
        codigo_in.setText(null);
        cedula_in.setText(null);
        nombre_in.setText(null);
        fechaNac_in.setText(null);
        signoSelect.setValue(null);
    }

    //---------------------------------------------------------------------------
    //Metodo para buscar un registro
    private void buscarPersona(String QUERY){
        try(
                Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);
        ){
            if (rs.next()){
                //Recorre la DB y guarda los datos en los atributos de la clase Persona
                persona.setCodigo(rs.getString("codigo_pers"));
                persona.setCedula(rs.getString("cedula_pers"));
                persona.setNombre(rs.getString("nombre_pers"));
                persona.setFechaNac(rs.getString("fechaNac_pers"));
                persona.setSignoZod(rs.getString("signoZod_pers"));

                actualizarDatosPersona();
            } else{
                new Alert(Alert.AlertType.ERROR,"Persona no encontrada!").showAndWait();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //Metodo para conexion a base de datos y ejecucion de query
    public void modificarRegistro(String QUERY){
        try(Connection connection = DriverManager.getConnection(DB_URL,USER,PASS)){
            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(QUERY);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //Metodo para insertar registro
    private void insertPersona(Persona persona){
        String insertQuery = "INSERT INTO personas VALUES ('"+persona.getCodigo()+"','"+persona.getCedula()+"','"+persona.getNombre()+"','"+persona.getFechaNac()+"','"+persona.getSignoZod()+"')";
        modificarRegistro(insertQuery);
    }

    //Metodo para actualizar los atributos de la clase Persona
    private void actualizarDatosPersona(){
        codigo_in.setText(persona.getCodigo());
        cedula_in.setText(persona.getCedula());
        nombre_in.setText(persona.getNombre());
        fechaNac_in.setText(persona.getFechaNac());
        signoSelect.setValue(persona.getSignoZod());
    }
}
