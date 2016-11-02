package com.example.equipo.pedidosiglesia.Controller;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.equipo.pedidosiglesia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_Lista_Actividades extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected ListView list_actividades;
    protected ArrayAdapter arrayAdapter;

    protected   Button btnNuevaActividad;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragment_Lista_Actividades() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Lista_Actividades.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Lista_Actividades newInstance(String param1, String param2) {
        Fragment_Lista_Actividades fragment = new Fragment_Lista_Actividades();
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
        String actividad; String Precio;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__lista__actividades, container, false);
        btnNuevaActividad = (Button) view.findViewById(R.id.btnNuevaActividad_listAct);
        btnNuevaActividad.setOnClickListener(this);
        list_actividades = (ListView) view.findViewById(R.id.list_actividades);
        //String[] arrayRegistrados = {"Victor", "Rafael", "Marta"};
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        JSONObject object4 = new JSONObject();
        String[] vec= new String[8];
        try {
            object.put("name" , "Empanadas");
            object.put("fecha", "31/10/2016");
            vec[0] = object.getString("name");
            vec[1] = object.getString("fecha");
            object2.put("name2" , "Postres");
            object2.put("fecha2", "04/11/2016");
            vec[2] = object2.getString("name2");
            vec[3] = object2.getString("fecha2");
            object3.put("name3" , "Almuerzos");
            object3.put("fecha3", "05/11/2016");
            vec[4] = object3.getString("name3");
            vec[5] = object3.getString("fecha3");
            object4.put("name4" , "Hallacas");
            object4.put("fecha4", "15/11/2016");
            vec[6] = object4.getString("name4");
            vec[7] = object4.getString("fecha4");
//
        } catch (JSONException e) {
            e.printStackTrace();
        }
        arrayAdapter = new ArrayAdapter(getContext(),R.layout.layout_items_registrador,R.id.txt_registrados,vec);
        list_actividades.setAdapter(arrayAdapter);
        return view;
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
            case R.id.btnNuevaActividad_listAct:
                /*Toast.makeText(getContext(), "Nueva Actividad", Toast.LENGTH_SHORT).show();
                fragmento = new Fragment_Registrar_Actividad();*/
                setDialogRegistrarActividad();

                //Intent intent = new Intent(getContext(),fragmento.getClass());
                //startActivity(intent);

                break;
        }
    }

    private void setDialogRegistrarActividad()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment__registrar__actividad);
        dialog.setTitle("Agregar Actividad");


        // set the custom dialog components - text, image and button
        Spinner spinner_actividades = (Spinner) dialog.findViewById(R.id.cbox_tipo_actividades);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getContext(), R.array.tipo_actividades , android.R.layout.simple_spinner_item);
        spinner_actividades.setAdapter(spinner_adapter);
        Button btnRegistrarActividad = (Button) dialog.findViewById(R.id.btnRegistrarActividad);
        // if button is clicked, close the custom dialog
        btnRegistrarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnRegistrarActividad:
                        Toast.makeText(getContext(), "Guardando...", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                }
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
