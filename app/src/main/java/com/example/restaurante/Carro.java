package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

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