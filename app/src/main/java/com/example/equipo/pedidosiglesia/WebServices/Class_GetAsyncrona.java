package com.example.equipo.pedidosiglesia.WebServices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rn_dr on 11/07/16.
 */
public class Class_GetAsyncrona extends AsyncTask<String,Integer,String> {

    private Context mcontext;
    private ProgressDialog progressDialog;

    public interface AsyncResponse {
        void processFinish(String output) throws JSONException;
    }
    public AsyncResponse delegate = null;

    public Class_GetAsyncrona(Context context, AsyncResponse delegate){
        mcontext = context;
        progressDialog = new ProgressDialog(mcontext);
        this.delegate = delegate;
    }

    public void execute() {
        // TODO Auto-generated method stub
    }


    protected String doInBackground(String... params) {
        String resul = "";
        try
        {
            String api = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet del = new HttpGet(api);
            del.setHeader("content-type", "application/json");
            HttpResponse resp = httpClient.execute(del);
            String respuesta = EntityUtils.toString(resp.getEntity());
            Log.e("Error: ", respuesta);
            JSONObject jsonresultentrada = new JSONObject(respuesta);
            String resultado = String.valueOf(jsonresultentrada.get("error"));
            if (!resultado.equals("false")){
                resul = "";
            }else {
                resul = respuesta;
            }
            if(resultado.equals("token_expired")){
                resul = "Tu sesión ha expirado. Volviendo a pantalla de inicio de sesión.";
            }
        }catch(Exception ex)
        {
            Log.e("ServicioRest", "Error!", ex);
            return "";
        }
        return resul;
    }

    public void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            if ((this.progressDialog != null) && this.progressDialog.isShowing()) {
                delegate.processFinish(result);
                this.progressDialog.dismiss();
                //this.progressDialog.cancel();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            this.progressDialog = null;
        }

        //Se retorna un string que contiene un JSON con los datos obtenidos
    }

    @Override
    protected void onPreExecute() {
        this.progressDialog.setMessage("Consultando...");
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.show();
    }

}