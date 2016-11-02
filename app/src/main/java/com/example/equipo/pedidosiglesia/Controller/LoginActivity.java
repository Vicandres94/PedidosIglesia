package com.example.equipo.pedidosiglesia.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.equipo.pedidosiglesia.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    // UI references.

    private EditText etUsuario;
    protected Button btnIniciar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        btnIniciar = (Button) findViewById(R.id.Iniciar);
        btnIniciar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.Iniciar:
                finish();
                //Intent intent = new Intent(this,ListaActividad.class);
                Intent intentBarra = new Intent(this, Barra_Menu.class);
                //startActivity(intent);
                startActivity(intentBarra);
            break;
        }
    }
}

