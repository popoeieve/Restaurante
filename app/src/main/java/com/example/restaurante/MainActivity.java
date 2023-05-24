package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button botonComenzar;

    Button botonConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonComenzar=(Button)findViewById(R.id.comenzarBtn);

        botonComenzar.setOnClickListener(v -> {
            Intent i=new Intent(MainActivity.this,Principal.class);
            startActivity(i);
        });

        botonConfiguracion = findViewById(R.id.botonConfiguracion);
        botonConfiguracion.setOnClickListener(v -> {
            // Crea una instancia del fragmento de configuración
            MenuConfiguracion dialogFragment = new MenuConfiguracion();
            dialogFragment.show(getSupportFragmentManager(), "MenuConfiguracion");
        });

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

}