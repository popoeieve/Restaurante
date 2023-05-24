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
    private String id;
    Button botonSalir;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_popup, null);

        // Obtener los datos pasados como argumentos
        nombre = getArguments().getString("nombre");
        precio = getArguments().getString("precio");
        id=getArguments().getString("id");

        TextView textViewNombre = view.findViewById(R.id.textTituloConf);
        TextView textViewPrecio = view.findViewById(R.id.lblNumeroMesa);

        textViewNombre.setText(nombre);
        textViewPrecio.setText(precio);

        botonSalir = view.findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> {
            dismiss();
        });
        Button botonAgregarCarroPopup = view.findViewById(R.id.botonGuardarSalir);
        botonAgregarCarroPopup.setOnClickListener(v -> {
            // Crear un nuevo objeto Plato con los datos actuales
            Plato plato = new Plato(id,nombre, precio);
            // Agregar el plato a la lista en PlatoSingleton
            ListaCarrito.getInstance().agregarPlato(plato);
            // Cerrar el diálogo
            dismiss();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    public static PlatoFragment newInstance(String id,String nombre, String precio) {
        PlatoFragment fragment = new PlatoFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("nombre", nombre);
        args.putString("precio", precio);
        fragment.setArguments(args);
        return fragment;
    }

}
