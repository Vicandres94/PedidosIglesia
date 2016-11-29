package com.example.equipo.pedidosiglesia.Modelo;


public class Productos {
    protected int productosId;
    protected int categoriasId;
    protected String producto;
    protected int valor;



    public Productos(int productosId, int categoriasId, String producto, int valor) {
        this.productosId = productosId;
        this.categoriasId = categoriasId;
        this.producto = producto;
        this.valor = valor;
    }

    public int getProductosId() {
        return productosId;
    }

    public void setProductosId(int productosId) {
        this.productosId = productosId;
    }

    public int getCategoriasId() {
        return categoriasId;
    }

    public void setCategoriasId(int categoriasId) {
        this.categoriasId = categoriasId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
