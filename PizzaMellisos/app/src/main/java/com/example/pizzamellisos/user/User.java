package com.example.pizzamellisos.user;

public class User {
    private String uid;
    private String nombre;
    private String apellido;
    private String ciudad;
    private String correo;
    private String edad;
    private String sexo;
    private String telefono;
    private String password;

    public User() {
    }


    public User(String uid, String nombre, String apellido, String ciudad, String correo, String edad, String sexo, String telefono, String password) {
        this.uid = uid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciudad = ciudad;
        this.correo = correo;
        this.edad = edad;
        this.sexo = sexo;
        this.telefono = telefono;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
