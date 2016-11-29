package com.example.equipo.pedidosiglesia.Controller;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.equipo.pedidosiglesia.Modelo.Categorias;
import com.example.equipo.pedidosiglesia.R;
import com.example.equipo.pedidosiglesia.WebServices.Class_GetAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_PostAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_PutAsyncrona;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_Lista_Categorias;
import com.example.equipo.pedidosiglesia.WebServices.Class_SP_login;
import com.example.equipo.pedidosiglesia.WebServices.Class_map_to_Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_List_Categorias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_List_Categorias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_List_Categorias extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    protected ListView list_categorias;
    protected ArrayAdapter arrayAdapter;
    protected Button btnNuevaCategoria;
    protected EditText etCategoria;
    protected JSONArray categoriasJson;
    protected JSONObject categoriaObJson;
    Class_SP_Lista_Categorias sp_lista_categorias;
    String[] vec;

    public Fragment_List_Categorias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_List_Categorias.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_List_Categorias newInstance(String param1, String param2) {
        Fragment_List_Categorias fragment = new Fragment_List_Categorias();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_list_categorias, container, false);

        btnNuevaCategoria = (Button) view.findViewById(R.id.btnNuevaCategoria_listaCategorias);
        btnNuevaCategoria.setOnClickListener(this);
        list_categorias = (ListView) view.findViewById(R.id.listView_ListaCategorias);
        list_categorias.setOnItemLongClickListener(new ItemPresionado());
        list_categorias.setOnItemClickListener(new Item());
        obtenerCategorias();
        return view;
    }

    private void setDialogRegistrarCategoria()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_registrar_categoria);
        dialog.setTitle("Agregar Categoria");
        // set the custom dialog components - text, image and button
        Button btnRegistrarCategoria = (Button) dialog.findViewById(R.id.btnRegistrarCategoria);
        etCategoria = (EditText) dialog.findViewById(R.id.et_Categoria);
        // if button is clicked, close the custom dialog
        btnRegistrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnRegistrarCategoria:

                        //Toast.makeText(getContext(),etCategoria.getText().toString() , Toast.LENGTH_SHORT).show();
                        if (etCategoria.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "Campos Vacios!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                Categorias categoria = new Categorias(etCategoria.getText().toString());
                 Class_PostAsyncrona postAsyncrona = (Class_PostAsyncrona) new Class_PostAsyncrona(Class_map_to_Json.JSONObject_Categorias(categoria), getContext(), new Class_PostAsyncrona.AsyncResponse() {

                                    @Override
                                    public void processFinish(String output) throws JSONException {
                                        JSONObject respuesta = new JSONObject(output);
                                        //Toast.makeText(getContext(), "Ejecutando....", Toast.LENGTH_SHORT).show();
                                        if ((respuesta.getString("error").toString()).equals("false")) {
                                            Toast.makeText(getContext(), respuesta.getString("mensaje"), Toast.LENGTH_SHORT).show();
                                            JSONArray categoriasJson = new JSONArray(Class_SP_Lista_Categorias.getCategoriasList(getContext()));
                                            vec = new String[categoriasJson.length()+1];
                                            for (int i = 0; i < categoriasJson.length(); i++) {
                                                vec[i] = categoriasJson.getJSONObject(i).getString("categoria");
                                            }
                                            vec[categoriasJson.length()] = etCategoria.getText().toString();
                                            arrayAdapter = new ArrayAdapter(getContext(),R.layout.layout_items_registrador,R.id.txt_registrados,vec);
                                            list_categorias.setAdapter(arrayAdapter);
                                        } else {
                                            //Log.d("Error", output);
                                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).execute("http://android.diosfuentedepodervalledupar.com/public/api/CrearCategoria?token=" + Class_SP_login.getToken(getContext()));
                                dialog.dismiss();
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

    private void setDialogMenuItem()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment__sub_menu_categoria);
        dialog.setTitle("Detalles Categoria");
        // set the custom dialog components - text, image and button
        Button btnModificarCategoria = (Button) dialog.findViewById(R.id.btnModificarCategoria);
        Button btnEliminarCategoria = (Button) dialog.findViewById(R.id.btnEliminarrCategoria);
        etCategoria = (EditText) dialog.findViewById(R.id.et_Categoria_Menu);
        try {
            etCategoria.setText(categoriaObJson.getString("categoria").toString(), TextView.BufferType.NORMAL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // if button is clicked, close the custom dialog
        btnModificarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (etCategoria.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "Campos Vacios!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                Categorias categoria = new Categorias( Integer.parseInt(categoriaObJson.getString("categoriasId")),etCategoria.getText().toString());
                                Class_PostAsyncrona postAsyncrona = (Class_PostAsyncrona) new Class_PostAsyncrona(Class_map_to_Json.JSONObject_Categorias(categoria), getContext(), new Class_PostAsyncrona.AsyncResponse() {
                                    @Override
                                    public void processFinish(String output) throws JSONException {
                                        JSONObject respuesta = new JSONObject(output);
                                        if ((respuesta.getString("error").toString()).equals("false")) {
                                            Toast.makeText(getContext(), respuesta.getString("mensaje"), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).execute("http://android.diosfuentedepodervalledupar.com/public/api/ModificarCategoria?token=" + Class_SP_login.getToken(getContext()));
                                obtenerCategorias();
                                dialog.dismiss();
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
            }
        });

        btnEliminarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Categorias categoria = new Categorias( Integer.parseInt(categoriaObJson.getString("categoriasId")));
                    Class_PutAsyncrona putAsyncrona = (Class_PutAsyncrona) new Class_PutAsyncrona(Class_map_to_Json.JSONObject_Categorias(categoria), getContext(), new Class_PutAsyncrona.AsyncResponse(){

                        @Override
                        public void processFinish(String output) throws JSONException {
                            JSONObject respuesta = new JSONObject(output);
                            if ((respuesta.getString("error").toString()).equals("false")) {
                                Toast.makeText(getContext(), respuesta.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).execute("http://android.diosfuentedepodervalledupar.com/public/api/EliminarCategoria?token=" + Class_SP_login.getToken(getContext()));
                    obtenerCategorias();
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

    public void obtenerCategorias(){
        Class_SP_Lista_Categorias.deleteListaCategoria(getContext());
        Class_GetAsyncrona getAsyncrona = (Class_GetAsyncrona) new Class_GetAsyncrona(getContext(), new Class_GetAsyncrona.AsyncResponse()
        {
            @Override
            public void processFinish(String output) throws JSONException {
                JSONObject respuesta = new JSONObject(output);
                //-----------------------------------------------------------------------------------
                categoriasJson = respuesta.getJSONArray("datos");
                vec = new String[categoriasJson.length()];
                for (int i = 0; i < categoriasJson.length(); i++) {
                    JSONObject c = categoriasJson.getJSONObject(i);
                    sp_lista_categorias.setListaCategorias(
                            getContext(),
                            respuesta.getJSONArray("datos").toString()
                    );
                    vec[i] = categoriasJson.getJSONObject(i).getString("categoria");
                }
                arrayAdapter = new ArrayAdapter(getContext(),R.layout.layout_items_registrador,R.id.txt_registrados,vec);
                list_categorias.setAdapter(arrayAdapter);
            }
        }).execute("http://android.diosfuentedepodervalledupar.com/public/api/GetCategorias?token="+ Class_SP_login.getToken(getContext()));
    }

    class  ItemPresionado implements  AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            String ojb  = categoriasJson.optString(position);
            try {
                categoriaObJson = new JSONObject(ojb);
                setDialogMenuItem();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    class Item implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getContext(), "Mantenga Presionado para Editar o Eliminar elemento.." , Toast.LENGTH_SHORT).show();
        }
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
            case R.id.btnNuevaCategoria_listaCategorias:
                setDialogRegistrarCategoria();
                break;

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
