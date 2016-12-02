package com.example.equipo.pedidosiglesia.Modelo;

import java.util.Date;

/**
 * Created by Equipo on 1/12/2016.
 */
public class Actividades {
    private int actividadesId;
    private int categoriasId;
    private Date fecha;
    private String estado;
    private String fechaString;
    private int invesion;
    private int recaudo;
    private int iglesiasId;

    public Actividades(int categoriasId, String fecha, String estado, int iglesiasId) {
        this.categoriasId = categoriasId;
        this.fechaString = fecha;
        this.estado = estado;
        this.iglesiasId = iglesiasId;
    }

    public int getActividadesId() {
        return actividadesId;
    }

    public void setActividadesId(int actividadesId) {
        this.actividadesId = actividadesId;
    }

    public int getCategoriasId() {
        return categoriasId;
    }

    public void setCategoriasId(int categoriasId) {
        this.categoriasId = categoriasId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getInvesion() {
        return invesion;
    }

    public void setInvesion(int invesion) {
        this.invesion = invesion;
    }

    public int getRecaudo() {
        return recaudo;
    }

    public void setRecaudo(int recaudo) {
        this.recaudo = recaudo;
    }

    public int getIglesiasId() {
        return iglesiasId;
    }

    public void setIglesiasId(int iglesiasId) {
        this.iglesiasId = iglesiasId;
    }

    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }
}
