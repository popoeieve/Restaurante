package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Principal extends AppCompatActivity {

    Button botonIdiomas,botonCarro,botonCarnes,botonBebidas,botonPostres,botonPescados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Desactiva el Cors temporalmente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView webView = new WebView(this);
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        botonIdiomas= findViewById(R.id.idiomasBtn);
        botonCarnes= findViewById(R.id.menuCarneBtn);
        botonBebidas=findViewById(R.id.menuBebidasBtn);
        botonPostres=findViewById(R.id.menuPostresBtn);
        botonPescados=findViewById(R.id.menuPescadosBtn);
        botonCarro=findViewById(R.id.carroBtn);

        botonIdiomas.setOnClickListener(v -> {
            Log.d("TAG", "Se ha ejecutado el método");
            // URL de la solicitud
            String url = "http://127.0.0.1/android/registro.php";

            // Solicitud GET
            StringRequest solicitud = new StringRequest(Request.Method.GET, url,
                    response -> {
                        Log.d("TAG", response); // Imprimir el resultado en la consola de registro
                        Toast.makeText(getApplicationContext(), "Salió bien", Toast.LENGTH_SHORT).show();
                    }, error -> {
                        Log.e("TAG", "Error en la solicitud: " + error.getMessage()); // Imprimir un mensaje de error en la consola de registro
                        Toast.makeText(getApplicationContext(), "Salió mal", Toast.LENGTH_SHORT).show();
                    });

            // Agregar la solicitud a la cola de solicitudes de Volley
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(solicitud);
            Log.d("TAG", "Se ha llegado al final del método");
            //new DownloadTask().execute("http://127.0.0.1/android/registro.php");
            //Intent i=new Intent(Principal.this,MainActivity.class);
            //startActivity(i);
        });

        botonCarnes.setOnClickListener(v -> {
            Intent j=new Intent(Principal.this,Menus.class);
            startActivity(j);
        });
        botonBebidas.setOnClickListener(v -> {
            Intent j=new Intent(Principal.this,Menus.class);
            startActivity(j);
        });
        botonPostres.setOnClickListener(v -> {
            Intent j=new Intent(Principal.this,Menus.class);
            startActivity(j);
        });
        botonPescados.setOnClickListener(v -> {
            Intent j=new Intent(Principal.this,Menus.class);
            startActivity(j);
        });

        botonCarro.setOnClickListener(v -> {
            Intent i=new Intent(Principal.this,Carro.class);
            startActivity(i);
        });

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
    /*
    private class DownloadTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();
                bufferedReader.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }

        protected void onPostExecute(String result) {
            // Aquí puedes actualizar la interfaz de usuario con los datos recibidos
            // por ejemplo, actualizar un ListView o TextView
        }
    }*/
}

