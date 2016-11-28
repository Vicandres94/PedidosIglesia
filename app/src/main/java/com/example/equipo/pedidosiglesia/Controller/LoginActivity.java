package com.example.equipo.pedidosiglesia.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.equipo.pedidosiglesia.Modelo.Users;
import com.example.equipo.pedidosiglesia.R;
import com.example.equipo.pedidosiglesia.WebServices.Class_GetAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_PostAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_Lista_Categorias;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_login;
import com.example.equipo.pedidosiglesia.WebServices.Class_map_to_Json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    // UI references.

    private EditText etUsuario;
    private EditText etPassword;

    protected Button btnIniciar;
    public String respuestaLogin = "true";
    JsonObjectRequest array;
    Class_SP_login sp_login;
    RequestQueue mRequestQueue;
    private final String url = "http://android.diosfuentedepodervalledupar.com/public/api/authenticate";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnIniciar = (Button) findViewById(R.id.Iniciar);
        btnIniciar.setOnClickListener(this);
        if (Class_SP_login.getToken(getApplicationContext()) != ""){
            Intent intentBarra = new Intent(getApplicationContext(), Barra_Menu.class);
            startActivity(intentBarra);
        }

    }

    @Override
    public void onClick(View v) {
        if ((etUsuario.getText().toString()).equals("") || (etPassword.getText().toString()).equals("")) {
            Toast.makeText(getApplicationContext(), "Campos  Vacíos!!!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            switch (v.getId()) {
                case R.id.Iniciar:
                    try {
                        Users users = new Users(etUsuario.getText().toString(), etPassword.getText().toString());
                        Class_PostAsyncrona postAsyncrona = (Class_PostAsyncrona) new Class_PostAsyncrona(Class_map_to_Json.JSONObject_login(users), this, new Class_PostAsyncrona.AsyncResponse()
                        {
                            @Override
                            public void processFinish(String output) throws JSONException {
                                Log.d("Error", output);
                                JSONObject respuesta = new JSONObject(output);
                                if ((respuesta.getString("error").toString()).equals("true")) {
                                    Toast.makeText(getApplicationContext(), "Usuario y/o Password incorrectos", Toast.LENGTH_SHORT).show();
                                } else {
                                    //finish();
                                    sp_login.setLogin(
                                            getApplicationContext(),
                                            String.valueOf(respuesta.getJSONObject("datos").getInt("usersId")),
                                            respuesta.getJSONObject("datos").getString("username"),
                                            respuesta.getJSONObject("datos").getString("nombres"),
                                            respuesta.getJSONObject("datos").getString("apellidos"),
                                            respuesta.getString("token"),
                                            (respuesta.getJSONObject("datos").getString("rolesId")),
                                            (respuesta.getJSONObject("datos").getString("iglesiasId")));
                                    //Toast.makeText(getApplicationContext(), Class_SP_login.getToken(getApplicationContext()).toString() , Toast.LENGTH_LONG).show();
                                    respuestaLogin = "false";
                                    Intent intentBarra = new Intent(getApplicationContext(), Barra_Menu.class);
                                    startActivity(intentBarra);
                                }
                            }
                        }).execute(url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }


}

