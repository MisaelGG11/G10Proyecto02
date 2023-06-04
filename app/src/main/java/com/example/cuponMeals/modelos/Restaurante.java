package com.example.cuponMeals.modelos;

public class Restaurante {
    private int id_restaurante;
    private String nombre_restaurante,direccion,id_google_maps, url_imagen,telefono;
    private double lat,lng;


    public Restaurante() {
    }

    public Restaurante(int id_restaurante, String nombre_restaurante,String direccion) {
        this.id_restaurante = id_restaurante;
        this.nombre_restaurante = nombre_restaurante;
        this.direccion = direccion;
        this.telefono="";
        this.lat = 0;
        this.lng = 0;
        this.id_google_maps = "";
        this.url_imagen = "";
    }
    public Restaurante( String nombre_restaurante,String direccion) {
        this.id_restaurante = 0;
        this.nombre_restaurante = nombre_restaurante;
        this.direccion = direccion;
        this.telefono="";
        this.lat = 0;
        this.lng = 0;
        this.id_google_maps = "";
        this.url_imagen = "";
    }
    public Restaurante(int id_restaurante, String id_google_maps, String nombre_restaurante, String direccion,String telefono, double lat, double lng, String url_imagen) {
        this.id_restaurante = id_restaurante;
        this.nombre_restaurante = nombre_restaurante;
        this.direccion = direccion;
        this.telefono=telefono;
        this.lat = lat;
        this.lng = lng;
        this.id_google_maps = id_google_maps;
        this.url_imagen = url_imagen;
    }
    public Restaurante(  String id_google_maps, String nombre_restaurante, String direccion,String telefono, double lat, double lng, String url_imagen) {
        this.id_restaurante = 0;
        this.nombre_restaurante = nombre_restaurante;
        this.direccion = direccion;
        this.telefono=telefono;
        this.lat = lat;
        this.lng = lng;
        this.id_google_maps = id_google_maps;
        this.url_imagen = url_imagen;
    }
    public Restaurante(int id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public int getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(int id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public String getNombre_restaurante() {
        return nombre_restaurante;
    }

    public void setNombre_restaurante(String nombre_restaurante) {
        this.nombre_restaurante = nombre_restaurante;
    }
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getId_google_maps() {
        return id_google_maps;
    }

    public void setId_google_maps(String id_google_maps) {
        this.id_google_maps = id_google_maps;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
