package com.example.cuponMeals.modelos;

public class TipoCupon {
    private int id_tipoCupon;
    private String nombre_tipo;

    public TipoCupon() {
    }

    public TipoCupon(int id_tipoCupon, String nombre_tipo) {
        this.id_tipoCupon = id_tipoCupon;
        this.nombre_tipo = nombre_tipo;
    }

    public int getId_tipoCupon() {
        return id_tipoCupon;
    }

    public void setId_tipoCupon(int id_tipoCupon) {
        this.id_tipoCupon = id_tipoCupon;
    }

    public String getNombre_tipo() {
        return nombre_tipo;
    }

    public void setNombre_tipo(String nombre_tipo) {
        this.nombre_tipo = nombre_tipo;
    }
}
