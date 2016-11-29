package com.example.equipo.pedidosiglesia.Controller;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.equipo.pedidosiglesia.R;
import com.example.equipo.pedidosiglesia.WebServices.Class_GetAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_Lista_Categorias;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_Lista_Productos;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Lista_Productos extends Fragment implements  View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected Button btnNuevoProducto;
    protected ListView list_productos;
    protected ArrayAdapter arrayAdapter;
    protected JSONArray productosJson;
    protected JSONObject productoObJson;
    Class_SP_Lista_Productos sp_lista_productos;
    private Spinner spinner_categorias;
    String[] vec;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragment_Lista_Productos() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fragment_Lista_Productos newInstance(String param1, String param2) {
        Fragment_Lista_Productos fragment = new Fragment_Lista_Productos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__lista__productos, container, false);
        btnNuevoProducto = (Button) view.findViewById(R.id.btnNuevoProducto_listaProductos);
        btnNuevoProducto.setOnClickListener(this);
        list_productos = (ListView) view.findViewById(R.id.list_productos);
        obtenerProductos();
        return view;
    }

    public void obtenerProductos(){
        Class_SP_Lista_Productos.deleteProducto(getContext());
        Class_GetAsyncrona getAsyncrona = (Class_GetAsyncrona) new Class_GetAsyncrona(getContext(), new Class_GetAsyncrona.AsyncResponse()
        {
            @Override
            public void processFinish(String output) throws JSONException {
                JSONObject respuesta = new JSONObject(output);
                //-----------------------------------------------------------------------------------
                productosJson = respuesta.getJSONArray("datos");
                vec = new String[productosJson.length()];
                for (int i = 0; i < productosJson.length(); i++) {
                    JSONObject c = productosJson.getJSONObject(i);

                    vec[i] = (productosJson.getJSONObject(i).getString("producto")).concat("     $"+ productosJson.getJSONObject(i).getString("valor"));
                }
                arrayAdapter = new ArrayAdapter(getContext(),R.layout.layout_items_registrador,R.id.txt_registrados,vec);
                list_productos.setAdapter(arrayAdapter);
            }
        }).execute("http://android.diosfuentedepodervalledupar.com/public/api/GetProductos?token="+ Class_SP_login.getToken(getContext()));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnNuevoProducto_listaProductos:
                setDialogRegistrarProducto();
                break;
        }
    }

    private void setDialogRegistrarProducto()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment__registrar__producto);
        dialog.setTitle("Agregar Producto");//
        // set the custom dialog components - text, image and button
        Button btnRegistrarProducto = (Button) dialog.findViewById(R.id.btnRegistrarProducto);
        spinner_categorias = (Spinner) dialog.findViewById(R.id.spinner_Categoria_RegistrarProducto);
        // if button is clicked, close the custom dialog
        Class_SP_Lista_Categorias.deleteListaCategoria(getContext());
        Class_GetAsyncrona get = (Class_GetAsyncrona) new Class_GetAsyncrona(getContext(), new Class_GetAsyncrona.AsyncResponse()
        {
            @Override
            public void processFinish(String output) throws JSONException {

                JSONObject respuesta = new JSONObject(output);
                List<String> list = new ArrayList<String>();
                //-----------------------------------------------------------------------------------
                JSONArray categoriasJson = respuesta.getJSONArray("datos");
                for (int i = 0; i < categoriasJson.length(); i++) {
                    list.add(categoriasJson.getJSONObject(i).getString("categoria"));
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,list);
                spinner_categorias.setAdapter(dataAdapter);
                //Toast.makeText(getContext(), list.toString(),Toast.LENGTH_SHORT).show();
            }
        }).execute("http://android.diosfuentedepodervalledupar.com/public/api/GetCategorias?token="+ Class_SP_login.getToken(getContext()));
        btnRegistrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnRegistrarProducto:
                        Toast.makeText(getContext(), "Guardando Producto...", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                }
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
