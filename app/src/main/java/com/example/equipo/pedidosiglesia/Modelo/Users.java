package com.example.equipo.pedidosiglesia.Modelo;

/**
 * Created by Equipo on 22/11/2016.
 */
public class Users {
    private int userId;
    private int rolesId;
    private int iglesiasId;
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private int identificacion;
    private String token;


    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRolesId() {
        return rolesId;
    }

    public void setRolesId(int rolesId) {
        this.rolesId = rolesId;
    }

    public int getIglesiasId() {
        return iglesiasId;
    }

    public void setIglesiasId(int iglesiasId) {
        this.iglesiasId = iglesiasId;
    }

    public String getUsername() {
        return username;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
