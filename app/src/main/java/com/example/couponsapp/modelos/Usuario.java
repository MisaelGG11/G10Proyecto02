package com.example.couponsapp.modelos;

public class Usuario {

    private int id_usuario;
    private int id_rol;
    private int id_restaurante;
    private String username;
    private String password;
    private String email;
    private String nombre;
    private String apellido;
    private String telefono;
    private int google_usuario;
    private String uri_foto_perfil;

    public Usuario() {
    }

    public Usuario(int id_usuario, int id_rol, int id_restaurante, String username, String password, String email, String nombre, String apellido, String telefono, int google_usuario,String uri_foto_perfil) {
        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
        this.id_restaurante = id_restaurante;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.google_usuario = google_usuario;
        this.uri_foto_perfil=uri_foto_perfil;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public int getId_restaurante() {
        return id_restaurante;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public void setId_restaurante(int id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getGoogle_usuario() {
        return google_usuario;
    }

    public void setGoogle_usuario(int google_usuario) {
        this.google_usuario = google_usuario;
    }

    public String getUri_foto_perfil() {
        return uri_foto_perfil;
    }

    public void setUri_foto_perfil(String uri_foto_perfil) {
        this.uri_foto_perfil = uri_foto_perfil;
    }
}
