package com.example.karinaquimbiamba.juegoinfantil.CapaEntidades;

public class Rol {

    public String uid;
    public String name, descripcion;
    //Creación de constructor por defecto sin parametros
    public Rol() {
    }
    //Genreación de constructor con parametos
   /* public Rol(String name, String descripcion) {
        this.name = name;
        this.descripcion = descripcion;
    }*/

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return name;
    }
}
