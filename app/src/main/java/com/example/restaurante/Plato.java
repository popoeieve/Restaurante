package com.example.restaurante;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

//Clase plato para el manejo de información
public class Plato extends Fragment implements Serializable {

    private String id,nombre,precio,tipo,ingredientes,descripcion,alergenos;
    private int cantidad;


    @Nullable
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Infla la vista del fragmento
        View view = getView();
        // Obtiene referencias a los elementos de la vista
        TextView textViewNombre = view.findViewById(R.id.text1Fragment);
        TextView textViewPrecio = view.findViewById(R.id.text2FragmentCarro);
        Button button = view.findViewById(R.id.botonFragmentPlatoCarro);
        // Configura los valores de los elementos de la vista
        textViewNombre.setText(getArguments().getString("nombre"));
        textViewPrecio.setText(getArguments().getString("precio"));
        button.setText(getArguments().getString("id"));
    }


    public Plato(String id, String nombre, String precio,int cantidad,String tipo,String ingredientes,String descripcion,String alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad= cantidad;
        this.tipo=tipo;
        this.ingredientes=ingredientes;
        this.descripcion=descripcion;
        this.alergenos=alergenos;

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

    public String get_tipo(){return tipo;}

    public String get_Ingredientes(){return ingredientes;}

    public String get_Descripcion(){return descripcion;}

    public String get_Alergenos(){return alergenos;}

    public int get_Cantidad() {
        return cantidad;
    }

    public void set_Cantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
