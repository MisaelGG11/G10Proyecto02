package com.example.couponsapp.modelos;

public class Permiso {
    private int id_permiso;
    private String nombre_permiso;

    public Permiso(int id_permiso, String nombre_permiso) {
        this.id_permiso = id_permiso;
        this.nombre_permiso = nombre_permiso;
    }

    public int getId_permiso() {
        return id_permiso;
    }

    public void setId_permiso(int id_permiso) {
        this.id_permiso = id_permiso;
    }

    public String getNombre_permiso() {
        return nombre_permiso;
    }

    public void setNombre_permiso(String nombre_permiso) {
        this.nombre_permiso = nombre_permiso;
    }
}
