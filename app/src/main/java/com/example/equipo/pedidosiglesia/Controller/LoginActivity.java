package com.example.equipo.pedidosiglesia.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.equipo.pedidosiglesia.R;
import com.example.equipo.pedidosiglesia.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    // UI references.

    private EditText etUsuario;
    private EditText etPassword;
    private ProgressBar progessBar;
    protected Button btnIniciar;
    JsonObjectRequest array;

    RequestQueue mRequestQueue;
    private final String url = "http://android.diosfuentedepodervalledupar.com/public/api/authenticate";
    private final String TAG = "PRUEBITA";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnIniciar = (Button) findViewById(R.id.Iniciar);
        progessBar = (ProgressBar) findViewById(R.id.progressBar);
        progessBar.setVisibility(View.INVISIBLE);
        btnIniciar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ((etUsuario.getText().toString()).equals("") || (etPassword.getText().toString()).equals("")) {
            Toast.makeText(getApplicationContext(), "Campos  Vacíos!!!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            switch (v.getId()) {
                case R.id.Iniciar:
                    progessBar.setVisibility(View.VISIBLE);
                    mRequestQueue = VolleySingleton.getInstance().getmRequestQueue();
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String token = response;
                            try {
                                JSONObject respuesta = new JSONObject(token);
                                //Toast.makeText(getApplicationContext(), respuesta.getString("token") , Toast.LENGTH_LONG).show();
                                if ((respuesta.getString("error").toString()).equals("true")) {
                                    Toast.makeText(getApplicationContext(), "Usuario y/o Password incorrectos", Toast.LENGTH_SHORT).show();
                                    progessBar.setVisibility(View.INVISIBLE);
                                } else {
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Bienvenido..", Toast.LENGTH_LONG).show();
                                    Intent intentBarra = new Intent(getApplicationContext(), Barra_Menu.class);
                                    progessBar.setVisibility(View.INVISIBLE);
                                    startActivity(intentBarra);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("username", etUsuario.getText().toString());
                            map.put("password", etPassword.getText().toString());
                            return map;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json");
                            return super.getHeaders();
                        }

                        @Override
                        public RetryPolicy getRetryPolicy() {
                           return new DefaultRetryPolicy(
                                    5000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                            );

                        }


                    };
                    mRequestQueue.add(request);

                    break;
            }
        }
    }
}

