package com.example.equipo.pedidosiglesia.WebServices;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rn_dr on 23/03/16.
 */
public class Class_SP_login {

    private SharedPreferences SPlogin;
    private static String PREF_NAME = "iglesia_datalogin";

    public Class_SP_login() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getUsersId(Context context) {
        return getPrefs(context).getString("usersId", "");
    }

    public static String getUsername(Context context) {
        return getPrefs(context).getString("username", "");
    }

    public static String getRolesId(Context context) {
        return getPrefs(context).getString("rolesId", "");
    }

    public static String getToken(Context context) {
        return getPrefs(context).getString("token", "");
    }


    public static String getIglesiasId(Context context) {
        return getPrefs(context).getString("iglesiasId", "");
    }

    public static String getNombres(Context context) {
        return getPrefs(context).getString("nombres", "");
    }

    public static String getApellidos(Context context) {
        return getPrefs(context).getString("apellidos", "");
    }

    public static void setLogin(Context context, String usersId, String username, String nombres, String apellidos, String token, String rolesId,  String iglesiasId ) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("usersId", usersId);
        editor.putString("username", username);
        editor.putString("nombres", nombres);
        editor.putString("apellidos", apellidos);
        editor.putString("token", token);
        editor.putString("iglesiasId", iglesiasId);
        editor.putString("rolesId", rolesId);
        editor.commit();
    }

    public static void deleteLogin(Context context) {
        setLogin(context,"","","","","","","");
    }

    public static String[] getLogin(Context spContext){
        String[] spl = new String[7];
        spl[0] = Class_SP_login.getUsersId(spContext);;
        spl[1] = Class_SP_login.getUsername(spContext);
        spl[2] = Class_SP_login.getNombres(spContext);
        spl[5] = Class_SP_login.getApellidos(spContext);
        spl[3] = Class_SP_login.getToken(spContext);;
        spl[4] = Class_SP_login.getRolesId(spContext);
        spl[6] = Class_SP_login.getIglesiasId(spContext);

        return spl;
    }

}
