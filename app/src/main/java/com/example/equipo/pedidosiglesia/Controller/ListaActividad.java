package com.example.equipo.pedidosiglesia.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.equipo.pedidosiglesia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Equipo on 26/10/2016.
 */
public class ListaActividad extends AppCompatActivity implements View.OnClickListener{
    protected ListView list_actividades;
    protected ArrayAdapter arrayAdapter;
    Adapter adapter;
    private   Button btnNuevaActividad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_lista_actividades);
        btnNuevaActividad = (Button) findViewById(R.id.btnNuevaActividad_listAct);
        btnNuevaActividad.setOnClickListener(this);
        list_actividades = (ListView) findViewById(R.id.list_actividades);
        String[] arrayRegistrados = {"Victor", "Rafael", "Marta"};
        JSONObject object = new JSONObject();
        String[] vec= new String[2];
        try {
            object.put("name","asdasd");
            object.put("telefono",2123);
            ArrayList arrayList = new ArrayList();
            vec[0] = object.getString("name");
            vec[1] = String.valueOf(object.getInt("telefono"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        arrayAdapter = new ArrayAdapter(this,R.layout.layout_items_registrador,R.id.txt_registrados,vec);
        list_actividades.setAdapter(arrayAdapter);

    }

    @Override
    public void onClick(View v)
    { showDialog(R.string.app_name);
        switch (v.getId())
        {
            case R.id.btnNuevaActividad_listAct:
                Intent intent = new Intent(this,RegistrarActividad.class);
                startActivity(intent);
                break;
        }
    }
}
