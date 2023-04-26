package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    Button botonIdiomas,botonCarro,botonCarnes,botonBebidas,botonPostres,botonPescados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        botonIdiomas= findViewById(R.id.idiomasBtn);
        botonCarnes= findViewById(R.id.menuCarneBtn);
        botonBebidas=findViewById(R.id.menuBebidasBtn);
        botonPostres=findViewById(R.id.menuPostresBtn);
        botonPescados=findViewById(R.id.menuPescadosBtn);
        botonCarro=findViewById(R.id.carroBtn);

        botonIdiomas.setOnClickListener(v -> {
            Intent i=new Intent(Principal.this,MainActivity.class);
            startActivity(i);
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
}