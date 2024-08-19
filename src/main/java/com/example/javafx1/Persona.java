package com.example.javafx1;

import java.sql.*;

public class Persona {
    private String codigo;
    private String cedula;
    private String nombre;
    private String fechaNac;
    private String signoZod;

    //Constructores
    public Persona(String codigo, String cedula, String nombre, String fechaNac, String signoZod) {
        this.codigo = codigo;
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.signoZod = signoZod;
    }
    public Persona() {}

    //Setters y Getters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getSignoZod() {
        return signoZod;
    }

    public void setSignoZod(String signoZod) {
        this.signoZod = signoZod;
    }
}
