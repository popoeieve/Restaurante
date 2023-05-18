package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Menus extends AppCompatActivity {

    Button botonIdiomas;
    Button botonCarro;

    String url = "http://192.168.0.15/android/registrolista.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        botonIdiomas=findViewById(R.id.idiomasBtn);
        botonCarro=findViewById(R.id.carroBtn);

        botonIdiomas.setOnClickListener(v -> {
            Intent i=new Intent(Menus.this,MainActivity.class);
            startActivity(i);
        });

        botonCarro.setOnClickListener(v -> {
            Intent i=new Intent(Menus.this,Carro.class);
            startActivity(i);
        });

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        RequestQueue queue = Volley.newRequestQueue(Menus.this);

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
                    PlatoMenuFragment fragment = new PlatoMenuFragment();
                    // Crea un Bundle para pasar los valores al fragmento
                    Bundle args = new Bundle();
                    args.putString("id", plato.get_Id());
                    args.putString("nombre", plato.get_Nombre());
                    args.putString("precio", "precio: "+plato.get_Precio());
                    fragment.setArguments(args);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.add(R.id.listaPlatosLayout, fragment);
                    fragmentTransaction.commit();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // below line is use to display a toast message along with our error.
            Toast.makeText(Menus.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
        });
        queue.add(jsonObjectRequest);


    }

}