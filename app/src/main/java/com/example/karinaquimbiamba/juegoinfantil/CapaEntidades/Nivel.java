package com.example.karinaquimbiamba.juegoinfantil.CapaEntidades;

public class Nivel {
    public String uid;
    public String uid_Area;
    public String name, descripcion;
    //Creación de constructor por defecto sin parametros
    public Nivel() {
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

    public String getUid_Area() {
        return uid_Area;
    }

    public void setUid_Area(String uid_Area) {
        this.uid_Area = uid_Area;
    }

    @Override
    public String toString() {
        return name;
    }


}
