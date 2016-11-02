package com.example.equipo.pedidosiglesia.Controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.equipo.pedidosiglesia.R;

public class Barra_Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener, Fragment_Lista_Actividades.OnFragmentInteractionListener,
        Fragment_Registrar_Actividad.OnFragmentInteractionListener, Fragment_Lista_Productos.OnFragmentInteractionListener,
        Fragment_Registrar_Producto.OnFragmentInteractionListener {


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
            super.onBackPressed();
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
        Toast.makeText(this,"Entro",Toast.LENGTH_SHORT).show();

        if (id == R.id.nav_Producto) {
            // Handle the camera action
            fragmento_seleccionado= true;
            fragmento = new Fragment_Lista_Productos();

        } else if (id == R.id.nav_Actividades) {

            fragmento_seleccionado= true;
            fragmento = new Fragment_Lista_Actividades();


        } else if (id == R.id.nav_Reportes) {

        } else if (id == R.id.nav_Personas) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
                Intent intent = new Intent(this,RegistrarActividad.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
