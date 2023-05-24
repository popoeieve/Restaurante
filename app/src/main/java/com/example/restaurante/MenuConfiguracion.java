package com.example.restaurante;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;


public class MenuConfiguracion extends DialogFragment {

    Button botonSalirConf;
    TextView textPlatoRecomend;
    TextView textNumMesa;

    Button botonGuardarSalir;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflar el diseño del fragmento del archivo XML
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_menu_configuracion, null);

        botonSalirConf=view.findViewById(R.id.botonSalirConf);
        botonGuardarSalir=view.findViewById(R.id.botonGuardarSalir);
        textPlatoRecomend=view.findViewById(R.id.textPlatoRecomend);
        textNumMesa=view.findViewById(R.id.textNumMesa);

        botonGuardarSalir.setOnClickListener(v -> {
            Context context = requireActivity().getApplicationContext();
            // Obtén una referencia a SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("MiArchivoPref", Context.MODE_PRIVATE);

            // Obtiene un editor para modificar los valores
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Almacena las variables "platoPromocional" y "mesaActual" con sus respectivos valores
            int platoPromocional = Integer.parseInt(textPlatoRecomend.getText().toString());
            int mesaActual = Integer.parseInt(textNumMesa.getText().toString());

            editor.putInt("platoPromocional", platoPromocional);
            editor.putInt("mesaActual", mesaActual);

            // Guarda los cambios
            editor.apply();
            dismiss();
        });

        botonSalirConf.setOnClickListener(v -> {
            dismiss();
        });

        // Configurar la vista personalizada en el diálogo
        builder.setView(view);

        return builder.create();
    }
}