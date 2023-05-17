package com.example.restaurante;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Plato extends Fragment {
    private String id;
    private String nombre;
    private String precio;
    @Nullable
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Infla la vista del fragmento
        View view = getView();
        // Obtiene referencias a los elementos de la vista
        TextView textViewNombre = view.findViewById(R.id.text1Fragment);
        TextView textViewPrecio = view.findViewById(R.id.text2Fragment);
        Button button = view.findViewById(R.id.botonFragmentPlatoMenu);
        // Configura los valores de los elementos de la vista
        textViewNombre.setText(getArguments().getString("nombre"));
        textViewPrecio.setText(getArguments().getString("precio"));
        button.setText(getArguments().getString("id"));
    }


    public Plato(String id, String nombre, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String get_Id() {
        return id;
    }

    public String get_Nombre() {
        return nombre;
    }

    public String get_Precio() {
        return precio;
    }


}
