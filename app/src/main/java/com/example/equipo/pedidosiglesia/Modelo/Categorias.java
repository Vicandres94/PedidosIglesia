package com.example.equipo.pedidosiglesia.Modelo;

/**
 * Created by Equipo on 23/11/2016.
 */
public class Categorias {
    private int categoriasId;
    private String categoria;

    public Categorias(String categoria) {
        this.categoria = categoria;
    }

    public Categorias(int categoriasId) {
        this.categoriasId = categoriasId;
    }

    public Categorias(int categoriasId, String categoria) {
        this.categoriasId = categoriasId;
        this.categoria = categoria;
    }

    public int getCategoriasId() {
        return categoriasId;
    }

    public void setCategoriasId(int categoriasId) {
        this.categoriasId = categoriasId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
