package com.example.restaurante;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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

    Button botonIdiomas,botonCarro,botonCarnes,botonBebidas,botonPostres,botonPescados,platoRecomendacion,botonLlamarCamareroPrincipal;

    TextView nombreRecomendacion, precioRecomendacion,textoRecomendacion;

    List<Plato> listaPlatos;

    String ip,url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        SharedPreferences sharedPreferences = getSharedPreferences("MiArchivoPref", Context.MODE_PRIVATE);


        // Obtén los valores de las variables almacenadas
        int platoRecomendadoInt = sharedPreferences.getInt("platoPromocional", 0);
        String platoRecomendado = String.valueOf(platoRecomendadoInt);
        ip=sharedPreferences.getString("ip","");//Toma valorNulo si falla al encontrar
        url = "http://"+ip+"/android/registrolista.php";

        botonLlamarCamareroPrincipal=findViewById(R.id.botonLlamarCamareroPrincipal);
        nombreRecomendacion=findViewById(R.id.nombreRecomendacion);
        precioRecomendacion=findViewById(R.id.precioRecomendacion);
        platoRecomendacion=findViewById(R.id.platoRecomendacion);
        textoRecomendacion=findViewById(R.id.descripcionRecomendacion);
        botonIdiomas= findViewById(R.id.idiomasBtn);
        botonCarnes= findViewById(R.id.menuCarneBtn);
        botonBebidas=findViewById(R.id.menuBebidasBtn);
        botonPostres=findViewById(R.id.menuPostresBtn);
        botonPescados=findViewById(R.id.menuPescadosBtn);
        botonCarro=findViewById(R.id.carroBtn);


        platoRecomendacion.setOnClickListener(v -> {
            // Obtener el plato seleccionado de la lista
            for (Plato platoSeleccionado : listaPlatos) {
                if(platoSeleccionado.get_Id().equals(platoRecomendado)){
                    // Obtener los valores del plato seleccionado
                    String nombre = platoSeleccionado.get_Nombre();
                    String precio = platoSeleccionado.get_Precio();
                    String id = platoSeleccionado.get_Id();
                    int cantidad=1;
                    String ingredientes = platoSeleccionado.get_Ingredientes();
                    String descripcion = platoSeleccionado.get_Descripcion();
                    String alergenos = platoSeleccionado.get_Alergenos();

                    // Crear una instancia del DialogFragment y pasar los valores como argumentos
                    PlatoFragment dialogFragment = PlatoFragment.newInstance(id, nombre, precio,cantidad,ingredientes,descripcion,alergenos);
                    dialogFragment.show(getSupportFragmentManager(), "plato_dialog");
                }
            }

        });

        botonIdiomas.setOnClickListener(v -> {
            Intent i=new Intent(Principal.this,MainActivity.class);
            startActivity(i);
        });

        botonLlamarCamareroPrincipal.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
            builder.setTitle("¿LLamar al camarero?")
                    .setMessage("¿Quiere llamar al camarero?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Mostrar un mensaje en un Toast como una llamada
                        Toast.makeText(getApplicationContext(), "Se ha llamado al camarero, por favor espere", Toast.LENGTH_LONG).show();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });


        botonCarnes.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("tipo", "hamburguesa");
            editor.apply();
            String tipo = sharedPreferences.getString("tipo", "");
            Intent j=new Intent(Principal.this,Menus.class);
            startActivity(j);
        });
        botonBebidas.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("tipo", "bebida");
            editor.apply();
            Intent j=new Intent(Principal.this,Menus.class);
            startActivity(j);
        });
        botonPostres.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("tipo", "postre");
            editor.apply();
            Intent j=new Intent(Principal.this,Menus.class);
            startActivity(j);
        });
        botonPescados.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("tipo", "complemento");
            editor.apply();
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

                listaPlatos = new ArrayList<>();

                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONObject value = jsonObject.getJSONObject(key);

                    Plato plato = new Plato(value.getString("id"), value.getString("nombre"), value.getString("precio"), 1,value.getString("tipo"), value.getString("ingredientes"), value.getString("descripcion"), value.getString("alergenos"));
                    listaPlatos.add(plato);
                }

                /*
                for (Plato plato : listaPlatos) {
                    Log.d("TAG", "ID: " + plato.get_Id() + ", Nombre: " + plato.get_Nombre() + ", Precio: " + plato.get_Precio());
                }
                 */

                for (Plato plato : listaPlatos) {
                    if(plato.get_Id().equals(platoRecomendado)){
                        nombreRecomendacion.setText(plato.get_Nombre());
                        precioRecomendacion.setText((plato.get_Precio()+" E"));
                        textoRecomendacion.setText(plato.get_Descripcion());
                        String nombreImagen = "a" + plato.get_Id();
                        Log.d("TAG","Buscando imagen de plato "+nombreImagen);
                        Drawable drawable = getResources().getDrawable(getResources().getIdentifier(nombreImagen, "drawable", getPackageName()));
                        platoRecomendacion.setBackground(drawable);
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

