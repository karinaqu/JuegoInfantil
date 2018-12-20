package com.example.karinaquimbiamba.juegoinfantil.CapaEntidades;

import android.widget.Chronometer;

public class Puntaje {

    public String idUsuario;
    public String idArea;
    public int vidas;
    public String idNivel;
    public String uid;
    public int puntaje;
    public String tiempo;


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public String getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(String idNivel) {
        this.idNivel = idNivel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "" +
                "Usuario: " + idUsuario  +
                ", Area: " + idArea +
                ", Nivel: " + idNivel  +
                ", Puntaje : " + puntaje ;
    }
}
