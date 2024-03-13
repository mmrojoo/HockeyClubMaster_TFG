package com.example.hockeyclubmaster.model;

public class Equipo {
    private String id;
    private String nombre;
    private String entrenador_UID;

    public Equipo() {
    }

    public Equipo(String id, String nombre, String entrenador_UID) {
        this.id = id;
        this.nombre = nombre;
        this.entrenador_UID = entrenador_UID;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEntrenador_UID() {
        return entrenador_UID;
    }
}

