package com.example.restaurante;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
//Clase que maneja el panel floante con el plato que se elije, vinculado con fragment_popup.xml
public class PlatoFragment extends DialogFragment {

    private String nombre;
    private String precio;
    Button botonSalir;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_popup, null);

        // Obtener los datos pasados como argumentos
        nombre = getArguments().getString("nombre");
        precio = getArguments().getString("precio");

        TextView textViewNombre = view.findViewById(R.id.textNombrePopup);
        TextView textViewPrecio = view.findViewById(R.id.textPrecioPopup);

        textViewNombre.setText(nombre);
        textViewPrecio.setText(precio);

        botonSalir = view.findViewById(R.id.botonCerrar);
        botonSalir.setOnClickListener(v -> {
            dismiss();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    public static PlatoFragment newInstance(String nombre, String precio) {
        PlatoFragment fragment = new PlatoFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("precio", precio);
        fragment.setArguments(args);
        return fragment;
    }

}
