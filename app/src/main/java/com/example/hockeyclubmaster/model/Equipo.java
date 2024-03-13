package com.example.hockeyclubmaster.model;

public class Equipo {
    private String categoria;
    private String UID_entrenador;

    // Constructor vacío necesario para Firebase Firestore
    public Equipo() {
        // Constructor sin argumentos requerido por Firebase Firestore
    }

    public Equipo(String categoria, String UID_entrenador) {
        this.categoria = categoria;
        this.UID_entrenador = UID_entrenador;
    }

    // Getters y setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUID_entrenador() {
        return UID_entrenador;
    }

    public void setUID_entrenador(String UID_entrenador) {
        this.UID_entrenador = UID_entrenador;
    }
}
