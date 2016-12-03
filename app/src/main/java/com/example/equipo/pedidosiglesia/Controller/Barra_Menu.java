package com.example.equipo.pedidosiglesia.Controller;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.equipo.pedidosiglesia.Modelo.Categorias;
import com.example.equipo.pedidosiglesia.Modelo.ListCategorias;
import com.example.equipo.pedidosiglesia.R;
import com.example.equipo.pedidosiglesia.WebServices.Class_GetAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_Lista_Categorias;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Barra_Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener, Fragment_Lista_Actividades.OnFragmentInteractionListener,
        Fragment_Registrar_Actividad.OnFragmentInteractionListener, Fragment_Lista_Productos.OnFragmentInteractionListener,
        Fragment_Registrar_Producto.OnFragmentInteractionListener,  Fragment_List_Categorias.OnFragmentInteractionListener {


    protected Fragment fragmento = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra__menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmento = new Fragment_Lista_Actividades();
        getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragmento ).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_barra__menu_drawer, menu);
        //super.onOptionsItemSelected(menu.getItem(g));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Boolean fragmento_seleccionado= false;

        if (id == R.id.nav_Producto) {
            // Handle the camera action
            fragmento_seleccionado= true;
            fragmento = new Fragment_Lista_Productos();

        } else if (id == R.id.nav_Actividades) {

            fragmento_seleccionado= true;
            fragmento = new Fragment_Lista_Actividades();


        } else if (id == R.id.nav_Reportes) {
            fragmento_seleccionado= true;
            //fragmento = new Fragment_Reportes();
            Intent intentBarra = new Intent(getApplicationContext(), Fragment_Reportes.class);
            startActivity(intentBarra);

        } else if (id == R.id.nav_Personas) {

        } else if (id == R.id.nav_Ajustes) {

        } else if (id == R.id.nav_Categoria){
            fragmento_seleccionado= true;
            fragmento = new Fragment_List_Categorias();
        }
        else if (id == R.id.nav_Salir) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Aviso!");
            dialogo1.setMessage("¿Desea Cerrar Sesión?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialogo1, int id) {
                    Class_SP_login.deleteLogin(getApplicationContext());
                    Class_SP_Lista_Categorias.deleteListaCategoria(getApplicationContext());
                    finish();
                }
            });

            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    dialogo1.dismiss();
                }
            });

            dialogo1.show();


        }

        if (fragmento_seleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragmento ).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        showDialog(R.string.app_name);
        switch (v.getId())
        {
            case R.id.btnNuevaActividad_listAct:
                Intent intent = new Intent(this,Fragment_Registrar_Actividad.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
