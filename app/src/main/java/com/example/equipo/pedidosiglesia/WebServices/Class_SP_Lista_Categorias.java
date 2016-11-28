package com.example.equipo.pedidosiglesia.WebServices;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rn_dr on 23/03/16.
 */
public class Class_SP_Lista_Categorias {

    private SharedPreferences SPlogin;
    private static String PREF_NAME = "iglesia_data_lista_categorias";

    public Class_SP_Lista_Categorias() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getCategoriasList(Context context) {
        return getPrefs(context).getString("categorias", "");
    }


    public static void setListaCategorias(Context context, String categoriasList) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("categorias", categoriasList);
        editor.commit();
    }

    public static void deleteListaCategoria(Context context) {
        setListaCategorias(context,"");
    }

    public static String[] getLogin(Context spContext){
        String[] spl = new String[2];
        spl[0] = Class_SP_Lista_Categorias.getCategoriasList(spContext);
        return spl;
    }

}
