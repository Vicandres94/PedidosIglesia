package com.example.equipo.pedidosiglesia.WebServices;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rn_dr on 23/03/16.
 */
public class Class_SP_Lista_Actividades {

    private SharedPreferences SPlogin;
    private static String PREF_NAME = "iglesia_dataActividad";

    public Class_SP_Lista_Actividades() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getActividadesId(Context context) {
        return getPrefs(context).getString("actividadesId", "");
    }

    public static String getCategoriaId(Context context) {
        return getPrefs(context).getString("categoriasId", "");
    }

    public static String getFecha(Context context) {
        return getPrefs(context).getString("fecha", "");
    }

    public static String getEstado(Context context) {
        return getPrefs(context).getString("estado", "");
    }

    public static String getInversion(Context context) {
        return getPrefs(context).getString("inversion", "");
    }

    public static String getRecaudo(Context context) {
        return getPrefs(context).getString("recaudo", "");
    }

    public static String getIglesiasId(Context context) {
        return getPrefs(context).getString("iglesiasId", "");
    }



    public static void setListaProducto(Context context, String actividadesId, String categoriasId, String fecha, String estado, String  inversion, String recaudo, String iglesiasId) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("actividadesId", actividadesId);
        editor.putString("categoriasId", categoriasId);
        editor.putString("fecha", fecha);
        editor.putString("estado", estado);
        editor.putString("inversion", inversion);
        editor.putString("recaudo", recaudo);
        editor.putString("iglesiasId", iglesiasId);
        editor.commit();
    }

    public static void deleteActividad(Context context) {
        setListaProducto(context,"","","","","","","");
    }

    public static String[] getListaActividades(Context spContext){
        String[] spl = new String[7];
        spl[0] = Class_SP_Lista_Actividades.getActividadesId(spContext);;
        spl[1] = Class_SP_Lista_Actividades.getCategoriaId(spContext);
        spl[2] = Class_SP_Lista_Actividades.getFecha(spContext);
        spl[3] = Class_SP_Lista_Actividades.getEstado(spContext);
        spl[4] = Class_SP_Lista_Actividades.getInversion(spContext);
        spl[5] = Class_SP_Lista_Actividades.getRecaudo(spContext);
        spl[6] = Class_SP_Lista_Actividades.getIglesiasId(spContext);
        return spl;
    }

}
