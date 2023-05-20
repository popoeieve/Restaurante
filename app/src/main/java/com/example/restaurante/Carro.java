package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Carro extends AppCompatActivity {

    Button botonVolver,botonIdiomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);

        botonVolver=findViewById(R.id.carroVolverBtn);
        botonIdiomas=findViewById(R.id.idiomasBtn);

        botonVolver.setOnClickListener(v -> {
            Intent i=new Intent(Carro.this,Principal.class);
            startActivity(i);
        });

        botonIdiomas.setOnClickListener(v -> {
            Intent i=new Intent(Carro.this,MainActivity.class);
            startActivity(i);
        });

        for (Plato plato : ListaCarrito.getInstance().getPlatos()) {
            PlatoCarroFragment fragment = new PlatoCarroFragment();
            // Crea un Bundle para pasar los valores al fragmento
            Bundle args = new Bundle();
            args.putString("id", plato.get_Id());
            args.putString("nombre", plato.get_Nombre());
            args.putString("precio", "precio: "+plato.get_Precio());
            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.listaPlatosCarro, fragment);
            fragmentTransaction.commit();
        }



        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void mostrarPlato(View view){
        PlatoFragment popupFragment = new PlatoFragment();
        popupFragment.show(getSupportFragmentManager(), "popup");
    }
}