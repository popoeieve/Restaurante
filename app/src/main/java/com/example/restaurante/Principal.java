package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Principal extends AppCompatActivity {

    String platoRecomendado="6"; //Ejemplo de plato recomendado
    Button botonIdiomas,botonCarro,botonCarnes,botonBebidas,botonPostres,botonPescados,platoRecomendacion;

    TextView nombreRecomendacion, precioRecomendacion;

    String url = "http://192.168.0.15/android/registrolista.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        nombreRecomendacion=findViewById(R.id.nombreRecomendacion);
        precioRecomendacion=findViewById(R.id.precioRecomendacion);
        platoRecomendacion=findViewById(R.id.platoRecomendacion);
        botonIdiomas= findViewById(R.id.idiomasBtn);
        botonCarnes= findViewById(R.id.menuCarneBtn);
        botonBebidas=findViewById(R.id.menuBebidasBtn);
        botonPostres=findViewById(R.id.menuPostresBtn);
        botonPescados=findViewById(R.id.menuPescadosBtn);
        botonCarro=findViewById(R.id.carroBtn);

        platoRecomendacion.setOnClickListener(v -> {
            String nombre = "pepinillos"; // Obtén el nombre del plato que deseas mostrar
            String precio = "Precio del plato"; // Obtén el precio del plato que deseas mostrar

            PlatoFragment popupFragment = PlatoFragment.newInstance(nombre, precio);
            popupFragment.show(getSupportFragmentManager(), "popup");
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

        RequestQueue queue = Volley.newRequestQueue(Principal.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                String respuesta= String.valueOf(response);
                JSONObject jsonObject = new JSONObject(respuesta);
                Iterator<String> keys = jsonObject.keys();

                List<Plato> listaPlatos = new ArrayList<>();

                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONObject value = jsonObject.getJSONObject(key);

                    Plato plato = new Plato(value.getString("id"), value.getString("nombre"), value.getString("precio"));
                    listaPlatos.add(plato);
                }

                for (Plato plato : listaPlatos) {
                    Log.d("TAG", "ID: " + plato.get_Id() + ", Nombre: " + plato.get_Nombre() + ", Precio: " + plato.get_Precio());
                }

                for (Plato plato : listaPlatos) {
                    // Crea un Bundle para pasar los valores al fragmento
                    if(plato.get_Id().equals(platoRecomendado)){
                        nombreRecomendacion.setText(plato.get_Nombre());
                        precioRecomendacion.setText((plato.get_Precio()));
                        platoRecomendacion.setText(plato.get_Id());
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // below line is use to display a toast message along with our error.
            Toast.makeText(Principal.this, "Error de conexión..", Toast.LENGTH_LONG).show();
        });
        queue.add(jsonObjectRequest);


    }
}

