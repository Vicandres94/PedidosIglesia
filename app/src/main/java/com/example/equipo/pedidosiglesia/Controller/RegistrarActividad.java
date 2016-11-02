package com.example.equipo.pedidosiglesia.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.equipo.pedidosiglesia.R;

/**
 * Created by Equipo on 26/10/2016.
 */
public class RegistrarActividad extends AppCompatActivity implements View.OnClickListener{
    protected Button btnGuardar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_registrar_actividad);
        btnGuardar = (Button) findViewById(R.id.btnRegistrarActividad);
        btnGuardar.setOnClickListener(this);
        Spinner spinner_actividades = (Spinner) findViewById(R.id.cbox_tipo_actividades);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.tipo_actividades , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_actividades.setAdapter(spinner_adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.btnRegistrarActividad:
                    finish();
                    Intent intent = new Intent(this,ListaActividad.class);
                    startActivity(intent);
                break;
        }
    }
}
