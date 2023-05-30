package com.example.restaurante;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    Button botonIdiomas,botonCarro,botonLlamarCamareroMenus,botonMenuHamburguesa,botonMenuComplemento,botonMenuBebida,botonMenuPostre;

    String url = "http://192.168.0.115/android/registrolista.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        botonLlamarCamareroMenus=findViewById(R.id.botonLlamarCamareroMenu);
        botonIdiomas=findViewById(R.id.idiomasBtn);
        botonCarro=findViewById(R.id.carroBtn);
        botonMenuHamburguesa=findViewById(R.id.menuHamburguesaBtn);
        botonMenuComplemento=findViewById(R.id.menuComplementosBtn);
        botonMenuBebida=findViewById(R.id.menuBebidasBtn);
        botonMenuPostre=findViewById(R.id.menuPostresBtn);

        botonLlamarCamareroMenus.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Menus.this);
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

        SharedPreferences sharedPreferences = getSharedPreferences("MiArchivoPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        botonMenuHamburguesa.setOnClickListener(v -> {
            vaciarMenu();
            editor.putString("tipo", "hamburguesa");
            editor.apply();
            cargarPlatosMenu();
        });

        botonMenuComplemento.setOnClickListener(v -> {
            vaciarMenu();
            editor.putString("tipo", "complemento");
            editor.apply();
            cargarPlatosMenu();
        });

        botonMenuBebida.setOnClickListener(v -> {
            vaciarMenu();
            editor.putString("tipo", "bebida");
            editor.apply();
            cargarPlatosMenu();
        });

        botonMenuPostre.setOnClickListener(v -> {
            vaciarMenu();
            editor.putString("tipo", "postre");
            editor.apply();
            cargarPlatosMenu();
        });

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

        cargarPlatosMenu();

    }

    public void vaciarMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        List<Fragment> fragmentos = fragmentManager.getFragments();
        if (fragmentos != null) {
            for (Fragment fragmento : fragmentos) {
                fragmentManager.beginTransaction().remove(fragmento).commit();
            }
        }
    }

    public void cargarPlatosMenu() {
        //Lee la base de datos y pasa los arguments para que se creen los fragment PlatoMenuFragment
        RequestQueue queue = Volley.newRequestQueue(Menus.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                String respuesta = String.valueOf(response);
                JSONObject jsonObject = new JSONObject(respuesta);
                Iterator<String> keys = jsonObject.keys();

                List<Plato> listaPlatos = new ArrayList<>();

                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONObject value = jsonObject.getJSONObject(key);

                    Plato plato = new Plato(value.getString("id"), value.getString("nombre"), value.getString("precio"), 1, value.getString("tipo"),value.getString("ingredientes"),value.getString("descripcion"),value.getString("alergenos"));
                    listaPlatos.add(plato);
                }

                //Filtro para que aparezca en el menú solo los platos del tipo seleccionado
                SharedPreferences sharedPreferences = getSharedPreferences("MiArchivoPref", MODE_PRIVATE);
                String tipo = sharedPreferences.getString("tipo", "");
                Log.d("TAG", "Se lee del tipo: " + tipo);


                for (Plato plato : listaPlatos) {
                    if (plato.get_tipo().equals(tipo)) {
                        PlatoMenuFragment fragment = new PlatoMenuFragment();
                        // Crea un Bundle para pasar los valores al fragmento
                        Bundle args = new Bundle();
                        args.putString("id", plato.get_Id());
                        args.putString("nombre", plato.get_Nombre());
                        args.putString("precio", plato.get_Precio());
                        args.putInt("cantidad", 1);
                        args.putString("descripcion", plato.get_Descripcion());
                        args.putString("ingredientes", plato.get_Ingredientes());
                        args.putString("alergenos", plato.get_Alergenos());
                        fragment.setArguments(args);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.add(R.id.listaPlatosLayout, fragment);
                        fragmentTransaction.commit();
                    }
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