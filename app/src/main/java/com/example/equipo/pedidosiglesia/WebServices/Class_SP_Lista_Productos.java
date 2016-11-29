package com.example.equipo.pedidosiglesia.WebServices;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rn_dr on 23/03/16.
 */
public class Class_SP_Lista_Productos {

    private SharedPreferences SPlogin;
    private static String PREF_NAME = "iglesia_dataProducto";

    public Class_SP_Lista_Productos() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getProductosId(Context context) {
        return getPrefs(context).getString("productosId", "");
    }

    public static String getCategoriaId(Context context) {
        return getPrefs(context).getString("categoriasId", "");
    }

    public static String getProducto(Context context) {
        return getPrefs(context).getString("producto", "");
    }

    public static String getValor(Context context) {
        return getPrefs(context).getString("valor", "");
    }



    public static void setListaProducto(Context context, String productosId, String categoriasId, String producto, String valor) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("productosId", productosId);
        editor.putString("categoriasId", categoriasId);
        editor.putString("producto", producto);
        editor.putString("valor", valor);
        editor.commit();
    }

    public static void deleteProducto(Context context) {
        setListaProducto(context,"","","","");
    }

    public static String[] getListaCategoria(Context spContext){
        String[] spl = new String[4];
        spl[0] = Class_SP_Lista_Productos.getProductosId(spContext);;
        spl[1] = Class_SP_Lista_Productos.getCategoriaId(spContext);
        spl[2] = Class_SP_Lista_Productos.getProducto(spContext);
        spl[3] = Class_SP_Lista_Productos.getValor(spContext);
        return spl;
    }

}
