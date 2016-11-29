package com.example.equipo.pedidosiglesia.WebServices;



import com.example.equipo.pedidosiglesia.Modelo.Categorias;
import com.example.equipo.pedidosiglesia.Modelo.Productos;
import com.example.equipo.pedidosiglesia.Modelo.Users;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rn_dr on 6/07/16.
 */
public class Class_map_to_Json {

    public static String JSONObject_login(Users users) throws JSONException {

        JSONObject dato = new JSONObject();
        dato.put("username", users.getUsername());
        dato.put("password", users.getPassword());
        return dato.toString();
    }

    public static String JSONObject_Categorias(Categorias categorias) throws JSONException {

        JSONObject dato = new JSONObject();
        dato.put("categoriasId", categorias.getCategoriasId());
        dato.put("categoria", categorias.getCategoria());
        return dato.toString();
    }

    public static String JSONObject_Productos(Productos productos) throws JSONException {

        JSONObject dato = new JSONObject();
        dato.put("productosId", productos.getProductosId());
        dato.put("categoriasId", productos.getCategoriasId());
        dato.put("producto", productos.getProducto());
        dato.put("valor", productos.getValor());
        return dato.toString();
    }

}
