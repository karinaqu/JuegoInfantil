package com.example.karinaquimbiamba.juegoinfantil;

public class User {
    public String uid;

    public String name, email;//declaración de atributos nombre y correo
    public String edad;//definción de atributo edad

    public User(){ //definición e metodo usuario sin atributos por defecto

    }

    public User(String name, String email, String edad){//definición de metodo con atributos para ser llamado
        this.name = name;//referenciar objeto nombre
        this.email= email;//Referenciar objeto correo
        this.edad= edad;//Referenciar objeto edad
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
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
