package com.example.equipo.pedidosiglesia.Modelo;


import java.util.ArrayList;

/**
 * Created by Equipo on 23/11/2016.
 */
public class ListCategorias
{
    protected ArrayList<Categorias> listaCategorias = new ArrayList<>();

    public ListCategorias() {
        
    }

    public ArrayList<Categorias> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(ArrayList<Categorias> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
}
