package com.example.cuponMeals.modelos;

import java.io.Serializable;

public class Cupon implements Serializable {
    private int id_cupon;
    private Restaurante restaurante;
    private TipoCupon tipoCupon;
    private String codigo_cupon;
    private String nombre_cupon;
    private String descripcion_cupon;
    private Double precio;
    private String horario_cupon;
    private int disponible;

    public Cupon() {
    }

    public Cupon(int id_cupon, Restaurante restaurante, TipoCupon tipoCupon, String codigo_cupon, String nombre_cupon, String descripcion_cupon, Double precio, String horario_cupon, int disponible) {
        this.id_cupon = id_cupon;
        this.restaurante = restaurante;
        this.tipoCupon = tipoCupon;
        this.codigo_cupon = codigo_cupon;
        this.nombre_cupon = nombre_cupon;
        this.descripcion_cupon = descripcion_cupon;
        this.precio = precio;
        this.horario_cupon = horario_cupon;
        this.disponible = disponible;
    }

    public int getId_cupon() {
        return id_cupon;
    }

    public void setId_cupon(int id_cupon) {
        this.id_cupon = id_cupon;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public TipoCupon getTipoCupon() {
        return tipoCupon;
    }

    public void setTipoCupon(TipoCupon tipoCupon) {
        this.tipoCupon = tipoCupon;
    }

    public String getCodigo_cupon() {
        return codigo_cupon;
    }

    public void setCodigo_cupon(String codigo_cupon) {
        this.codigo_cupon = codigo_cupon;
    }

    public String getNombre_cupon() {
        return nombre_cupon;
    }

    public void setNombre_cupon(String nombre_cupon) {
        this.nombre_cupon = nombre_cupon;
    }

    public String getDescripcion_cupon() {
        return descripcion_cupon;
    }

    public void setDescripcion_cupon(String descripcion_cupon) {
        this.descripcion_cupon = descripcion_cupon;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getHorario_cupon() {
        return horario_cupon;
    }

    public void setHorario_cupon(String horario_cupon) {
        this.horario_cupon = horario_cupon;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }
}
