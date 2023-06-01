package com.example.couponsapp.modelos;

import java.util.Date;

public class RegistrarCupon {
    private int id_registro;
    private Cupon cupon;
    private Usuario usuario;
    private String fecha_registro;

    public RegistrarCupon(int id_registro, Cupon cupon, Usuario usuario, String fecha_registro) {
        this.id_registro = id_registro;
        this.cupon = cupon;
        this.usuario = usuario;
        this.fecha_registro = fecha_registro;
    }

    public int getId_registro() {
        return id_registro;
    }

    public void setId_registro(int id_registro) {
        this.id_registro = id_registro;
    }

    public Cupon getCupon() {
        return cupon;
    }

    public void setCupon(Cupon cupon) {
        this.cupon = cupon;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
