package com.example.restaurante;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlatoDeCarro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlatoDeCarro extends DialogFragment {

    private DialogListener dialogListener;
    private String nombre,ingredientes;
    private String precio;
    private String id;
    private int cantidad;
    Button botonSalir;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_plato_de_carro, null);

        // Establecer el fondo redondeado
        view.setBackgroundResource(R.drawable.dialog_background);

        // Obtener los datos pasados como argumentos
        nombre = getArguments().getString("nombre");
        precio = getArguments().getString("precio");
        id=getArguments().getString("id");
        cantidad=getArguments().getInt("cantidad");
        ingredientes=getArguments().getString("ingredientes");

        TextView textViewNombre = view.findViewById(R.id.textTituloConf);
        TextView textViewPrecio = view.findViewById(R.id.lblNumeroMesa);
        TextView textViewIngredientes=view.findViewById(R.id.ingredientesCarro);
        ImageView imagen=view.findViewById(R.id.imagenPopup);
        String nombreImagen="a"+id;
        Drawable imagenPlato = requireContext().getResources().getDrawable(getResources().getIdentifier(nombreImagen, "drawable", requireContext().getPackageName()));
        imagen.setImageDrawable(imagenPlato);

        textViewNombre.setText(nombre);
        textViewPrecio.setText(precio+" E");
        textViewIngredientes.setText(ingredientes);

        botonSalir = view.findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> {
            dismiss();
        });
        Button botonEliminarPlatoDeCarro = view.findViewById(R.id.botonEliminarCarro);
        botonEliminarPlatoDeCarro.setOnClickListener(v -> {
            ListaCarrito listaCarrito = ListaCarrito.getInstance();
            List<Plato> platos = listaCarrito.getPlatos();
            Iterator<Plato> iterator = platos.iterator();
            while (iterator.hasNext()) {
                Plato plato = iterator.next();
                if (plato.get_Id().equals(id)) {
                    iterator.remove(); // Elimina el plato de la lista carro
                    break; // Salir del bucle si se encuentra un plato repetido
                }
            }

            // Cerrar el diálogo
            dialogListener.onDialogClosed();
            dismiss();
        });

        builder.setView(view);

        Dialog dialog = builder.create();

        // Cambiar el fondo de la ventana del diálogo a transparente
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Configurar otras propiedades del diálogo según sea necesario
        return dialog;
    }

    public static PlatoDeCarro newInstance(String id,String nombre, String precio,int cantidad,String ingredientes,String descripcion,String alergenos) {
        PlatoDeCarro fragment = new PlatoDeCarro();
        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("nombre", nombre);
        args.putString("precio", precio);
        args.putInt("cantidad",cantidad);
        args.putString("ingredientes",ingredientes);
        args.putString("descripcion",descripcion);
        args.putString("alergenos",alergenos);
        fragment.setArguments(args);
        return fragment;
    }

    public interface DialogListener {
        void onDialogClosed();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " debe implementar DialogListener");
        }
    }

}

