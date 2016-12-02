package com.example.equipo.pedidosiglesia.Controller;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.equipo.pedidosiglesia.Modelo.Actividades;
import com.example.equipo.pedidosiglesia.R;
import com.example.equipo.pedidosiglesia.WebServices.Class_GetAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_PostAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_PutAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_Lista_Actividades;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_Lista_Categorias;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_login;
import com.example.equipo.pedidosiglesia.WebServices.Class_map_to_Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Fragment_Lista_Actividades extends Fragment implements  View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected ListView list_actividades;
    protected ArrayAdapter arrayAdapter;
    protected Button btnNuevaActividad;
    protected JSONArray actividadesJson;
    protected JSONArray categoriasJson;
    protected JSONObject actividadObJson;
    private Spinner spinner_categorias;
    private static EditText et_Fecha, et_inversion;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String[] vec;
    private Date dateFecha;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragment_Lista_Actividades() {
        // Required empty public constructor
    }


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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__lista__actividades, container, false);
        //Toast.makeText(getContext(), Class_SP_login.getToken(getContext()).toString() , Toast.LENGTH_LONG).show();
        btnNuevaActividad = (Button) view.findViewById(R.id.btnNuevaActividad_listAct);
        btnNuevaActividad.setOnClickListener(this);
        list_actividades = (ListView) view.findViewById(R.id.list_actividades);
        list_actividades.setOnItemClickListener(new ItemList());
        list_actividades.setOnItemLongClickListener(new ItemPresionado());
        et_Fecha = (EditText) view.findViewById(R.id.et_fecha_registrar_actividad);
        obtenerActividades();
        return view;
    }



    public class itemFecha implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            setDialogRegistrarFecha();
        }
    }

    public void obtenerActividades(){
        Class_SP_Lista_Actividades.deleteActividad(getContext());
        Class_GetAsyncrona getAsyncronaActividades = (Class_GetAsyncrona) new Class_GetAsyncrona(getContext(), new Class_GetAsyncrona.AsyncResponse()
        {
            @Override
            public void processFinish(String output) throws JSONException {
                JSONObject respuesta = new JSONObject(output);
                //-----------------------------------------------------------------------------------

                if (respuesta.getString("error").equals("token_expired")){
                    Class_SP_login.deleteLogin(getContext());
                    Class_SP_Lista_Categorias.deleteListaCategoria(getContext());
                    Toast.makeText(getContext(), "Su Sesión ha Expirado, vuelva a Iniciarla....",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    //Toast.makeText(getContext(), respuesta.getString("error").toString(),Toast.LENGTH_SHORT).show();
                }

                actividadesJson = respuesta.getJSONArray("datos");
                vec = new String[actividadesJson.length()];
                for (int i = 0; i < actividadesJson.length(); i++) {
                    JSONObject c = actividadesJson.getJSONObject(i);
                   vec[i] ="Actividad De ".concat( (actividadesJson.getJSONObject(i).getString("categoria") + " \n Fecha: " + (actividadesJson.getJSONObject(i).getString("fecha"))));
                }
                arrayAdapter = new ArrayAdapter(getContext(),R.layout.layout_items_registrador,R.id.txt_registrados,vec);
                list_actividades.setAdapter(arrayAdapter);
            }
        }).execute("http://android.diosfuentedepodervalledupar.com/public/api/GetActividades?token="+ Class_SP_login.getToken(getContext()));
    }

    public  void llenarSpiner(){
        Class_SP_Lista_Categorias.deleteListaCategoria(getContext());
        Class_GetAsyncrona get = (Class_GetAsyncrona) new Class_GetAsyncrona(getContext(), new Class_GetAsyncrona.AsyncResponse()
        {
            @Override
            public void processFinish(String output) throws JSONException {

                JSONObject respuesta = new JSONObject(output);
                List<String> list = new ArrayList<String>();
                //-----------------------------------------------------------------------------------

                categoriasJson = respuesta.getJSONArray("datos");
                for (int i = 0; i < categoriasJson.length(); i++) {
                    list.add(categoriasJson.getJSONObject(i).getString("categoria"));
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,list);
                spinner_categorias.setAdapter(dataAdapter);
                //
            }
        }).execute("http://android.diosfuentedepodervalledupar.com/public/api/GetCategorias?token="+ Class_SP_login.getToken(getContext()));
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
                   setDialogRegistrarActividad();
                break;

        }
    }

    private void setDialogRegistrarActividad()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment__registrar__actividad);
        dialog.setTitle("Agregar Actividad");
        et_Fecha = (EditText) dialog.findViewById(R.id.et_fecha_registrar_actividad);
        et_Fecha.setOnClickListener(new itemFecha());
        spinner_categorias = (Spinner)dialog.findViewById(R.id.spinner_Categoria_Registrar_Actividad);
        llenarSpiner();
        // set the custom dialog components - text, image and button
        Button btnRegistrarActividad = (Button) dialog.findViewById(R.id.btnRegistrarActividad);
        // if button is clicked, close the custom dialog
        btnRegistrarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnRegistrarActividad:

                        try {
                            dateFecha = df.parse(String.valueOf(et_Fecha.getText()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (et_Fecha.getText().toString().equals("") || fechaMayor(dateFecha)){
                            Toast.makeText(getContext(), "Seleccione una Fecha Válida",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                int id = Integer.parseInt(categoriasJson.getJSONObject(spinner_categorias.getSelectedItemPosition()).getString("categoriasId"));
                                Actividades actividad = new Actividades(id,et_Fecha.getText().toString(),"Pendiente",1);
                                Class_PostAsyncrona postCrearActividad = (Class_PostAsyncrona) new Class_PostAsyncrona(Class_map_to_Json.JSONObject_Actividades(actividad), getContext(), new Class_PostAsyncrona.AsyncResponse() {

                                    @Override
                                    public void processFinish(String output) throws JSONException {
                                        JSONObject respuesta = new JSONObject(output);
                                        if ((respuesta.getString("error").toString()).equals("false")) {
                                            Toast.makeText(getContext(), respuesta.getString("mensaje"), Toast.LENGTH_SHORT).show();
                                        } else if (respuesta.getString("error").equals("token_expired")){
                                            Class_SP_login.deleteLogin(getContext());
                                            Class_SP_Lista_Categorias.deleteListaCategoria(getContext());
                                            Toast.makeText(getContext(), "Su Sesión ha Expirado, vuelva a Iniciarla....",Toast.LENGTH_SHORT).show();
                                            getActivity().finish();
                                            //Toast.makeText(getContext(), respuesta.getString("error").toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).execute("http://android.diosfuentedepodervalledupar.com/public/api/CrearActividad?token=" + Class_SP_login.getToken(getContext()));
                                dialog.dismiss();
                                obtenerActividades();
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public boolean fechaMayor(Date fechaR){
        Date actual = new Date();
        Calendar c = Calendar.getInstance();
        String formattedDate = df.format(c.getTime());
        try {
            actual = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return actual.after(fechaR);
    }

    private void setDialogRegistrarFecha()
    {
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getFragmentManager(),"Date Picker");
    }

    private void setDialogMenuItem()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment__modifcar__actividad);
        dialog.setTitle("Detalles Actividad");
        // set the custom dialog components - text, image and button
        et_Fecha = (EditText) dialog.findViewById(R.id.et_fecha_modificar_actividad);
        et_inversion = (EditText) dialog.findViewById(R.id.et_inversion);
        et_Fecha.setOnClickListener(new itemFecha());
        Button btnModificarActividad = (Button) dialog.findViewById(R.id.btnModificarActividad);
        Button btnEliminarActividad = (Button) dialog.findViewById(R.id.btnEliminarActividad);
        spinner_categorias = (Spinner) dialog.findViewById(R.id.spinner_Categoria_Modificar_Actividad);
        llenarSpiner();
        try {
            et_Fecha.setText(actividadObJson.getString("fecha").toString(), TextView.BufferType.NORMAL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // if button is clicked, close the custom dialog
        btnModificarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dateFecha = df.parse(String.valueOf(et_Fecha.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (et_Fecha.getText().toString().equals("") || fechaMayor(dateFecha)){
                    Toast.makeText(getContext(), "Seleccione una Fecha Válida",Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        int id = Integer.parseInt(categoriasJson.getJSONObject(spinner_categorias.getSelectedItemPosition()).getString("categoriasId"));
                        int idA = Integer.parseInt(actividadObJson.getString("actividadesId"));
                        Actividades actividad = new Actividades(idA,id,"Pendiente",et_Fecha.getText().toString(),Integer.parseInt(et_inversion.getText().toString()),1);
                        Class_PostAsyncrona postModificarActividad = (Class_PostAsyncrona) new Class_PostAsyncrona(Class_map_to_Json.JSONObject_Actividades(actividad), getContext(), new Class_PostAsyncrona.AsyncResponse() {
                            @Override
                            public void processFinish(String output) throws JSONException {
                                JSONObject respuesta = new JSONObject(output);
                                if ((respuesta.getString("error").toString()).equals("false")) {
                                    Toast.makeText(getContext(), respuesta.getString("mensaje"), Toast.LENGTH_SHORT).show();
                                } else if (respuesta.getString("error").equals("token_expired")){
                                    Class_SP_login.deleteLogin(getContext());
                                    Class_SP_Lista_Categorias.deleteListaCategoria(getContext());
                                    Toast.makeText(getContext(), "Su Sesión ha Expirado, vuelva a Iniciarla....",Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                    //Toast.makeText(getContext(), respuesta.getString("error").toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).execute("http://android.diosfuentedepodervalledupar.com/public/api/ModificarActividad?token=" + Class_SP_login.getToken(getContext()));
                        obtenerActividades();
                        dialog.dismiss();
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        btnEliminarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Actividades actividad = new Actividades(Integer.parseInt(actividadObJson.getString("actividadesId")));
                    Class_PutAsyncrona putEliminarActividad = (Class_PutAsyncrona) new Class_PutAsyncrona(Class_map_to_Json.JSONObject_Actividades(actividad), getContext(), new Class_PutAsyncrona.AsyncResponse(){

                        @Override
                        public void processFinish(String output) throws JSONException {
                            JSONObject respuesta = new JSONObject(output);
                            if ((respuesta.getString("error").toString()).equals("false")) {
                                Toast.makeText(getContext(), respuesta.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            } else if (respuesta.getString("error").equals("token_expired")){
                                Class_SP_login.deleteLogin(getContext());
                                Class_SP_Lista_Categorias.deleteListaCategoria(getContext());
                                Toast.makeText(getContext(), "Su Sesión ha Expirado, vuelva a Iniciarla....",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                //Toast.makeText(getContext(), respuesta.getString("error").toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).execute("http://android.diosfuentedepodervalledupar.com/public/api/EliminarActividad?token=" + Class_SP_login.getToken(getContext()));
                    obtenerActividades();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    class  ItemPresionado implements  AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            String ojb  = actividadesJson.optString(position);
            try {
                actividadObJson = new JSONObject(ojb);
                //Toast.makeText(getContext(),productoObJson.toString(),Toast.LENGTH_SHORT).show();
                setDialogMenuItem();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }
    }


    private void setDialogTomarPedidos()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment__tomar__pedidos);
        dialog.setTitle("Tomar Pedidos");
        ///////
        Button btnGuardarPedido = (Button) dialog.findViewById(R.id.btn_Guardar_Pedido);
        ListView list_Pedidos = (ListView) dialog.findViewById(R.id.list_tomar_Pedido);

        //String[] arrayRegistrados = {"Victor", "Rafael", "Marta"};
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        JSONObject object4 = new JSONObject();
        String[] vec= new String[8];
        try {
            object.put("name" , "Victor Medina");
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
        list_Pedidos.setAdapter(arrayAdapter);
        /////


        /*list_actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Tomar Pedidos", Toast.LENGTH_SHORT).show();
                setDialogTomarPedidos();
            }
        });*/


        // set the custom dialog components - text, image and button
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        public static String fecha;

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //Do something with the date chosen by the user
            month = month +1;
            fecha= year +  "-" + month +  "-" +day;
            et_Fecha.setText(fecha);
        }



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current date as the default date in the date picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getContext(), this, year, month, day);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

     class ItemList implements AdapterView.OnItemClickListener{

         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Toast.makeText(getContext(), "Lists", Toast.LENGTH_SHORT).show();
             setDialogTomarPedidos();
         }
     }
}


