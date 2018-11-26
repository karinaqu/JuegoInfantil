package com.example.karinaquimbiamba.juegoinfantil.CapaEntidades;

public class User {
    public String uid;

    public String name, email;//declaración de atributos nombre y correo
    public String rol;//definción de atributo edad

    public User(){ //definición e metodo usuario sin atributos por defecto

    }

    public User(String name, String email, String rol){//definición de metodo con atributos para ser llamado
        this.name = name;//referenciar objeto nombre
        this.email= email;//Referenciar objeto correo
        this.rol= rol;//Referenciar objeto edad
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override

    public String toString() {
        return name;
    }

}
