package com.example.equipo.pedidosiglesia.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by rn_dr on 27/02/16.
 */
public class  Class_PostAsyncrona extends AsyncTask<String,Integer,String> {
    private Context mContext;
    private String Mdatos;
    private ProgressDialog prgEnviando;


    public Class_PostAsyncrona(String datos, Context context, AsyncResponse delegate){
        Mdatos = datos;
        mContext = context;
        this.delegate = delegate;
        prgEnviando = new ProgressDialog(context);
    }

    public interface AsyncResponse {
        void processFinish(String output) throws JSONException;
    }
    public AsyncResponse delegate = null;

    @Override
    protected void onPreExecute() {
        this.prgEnviando.setMessage("Enviando...");
        this.prgEnviando.setCanceledOnTouchOutside(false);
        this.prgEnviando.show();
    }

    protected String doInBackground(String... params) {
        String resul = "";
        try {
            String api = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(api);
            post.setHeader("content-type", "application/json");

            StringEntity entity = new StringEntity(Mdatos);
            post.setEntity(entity);

            HttpResponse resp = httpClient.execute(post);

            String respStr = EntityUtils.toString(resp.getEntity());

            //Log.e("errorr",jsonresultentrada.toString());
                resul = respStr;


        }catch(Exception ex)
        {
            Log.e("ServicioRest", "Error!", ex);
            resul = "";
        }

        return resul;
    }


    @Override
    protected void onPostExecute(String aBoolean) {
        try {
            delegate.processFinish(aBoolean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        prgEnviando.dismiss();
    }
}

