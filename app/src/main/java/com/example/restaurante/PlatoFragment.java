package com.example.restaurante;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.List;

//Clase que maneja el panel flotante con el plato que se elije, vinculado con fragment_popup.xml
public class PlatoFragment extends DialogFragment {

    private String id,tipo,precio,nombre,ingredientes,descripcion,alergenos;

    private int cantidad;
    Button botonSalir;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_popup, null);

        view.setBackgroundResource(R.drawable.dialog_background);

        // Obtener los datos pasados como argumentos
        nombre = getArguments().getString("nombre");
        precio = getArguments().getString("precio");
        id=getArguments().getString("id");
        cantidad=1;
        tipo=getArguments().getString("tipo");
        ingredientes=getArguments().getString("ingredientes");
        descripcion=getArguments().getString("descripcion");
        alergenos=getArguments().getString("alergenos");

        //Los datos pasados se meten en las vistas de los platos
        TextView textViewNombre = view.findViewById(R.id.textTituloConf);
        TextView textViewPrecio = view.findViewById(R.id.lblNumeroMesa);
        ImageView imagen=view.findViewById(R.id.imagenPopup);
        String nombreImagen="a"+id;
        Drawable imagenPlato = requireContext().getResources().getDrawable(getResources().getIdentifier(nombreImagen, "drawable", requireContext().getPackageName()));
        imagen.setImageDrawable(imagenPlato);
        TextView textDescripcion=view.findViewById(R.id.descripcionPlato);
        textViewNombre.setText(nombre);
        textViewPrecio.setText(precio+" E");
        textDescripcion.setText(ingredientes);

        botonSalir = view.findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> {
            dismiss();
        });
        Button botonAgregarCarroPopup = view.findViewById(R.id.botonGuardarSalir);
        botonAgregarCarroPopup.setOnClickListener(v -> {
            ListaCarrito listaCarrito = ListaCarrito.getInstance();
            List<Plato> platos = listaCarrito.getPlatos();
            if (platos.isEmpty()) {
                // La lista de platos está vacía
                Log.d("ListaCarrito", "La lista de platos está vacía");
                Log.d("ListaCarrito", "Se agregó un plato nuevo");
                // Crear un nuevo objeto Plato si no está en el carrito
                Plato platoACarro = new Plato(id, nombre, precio,cantidad,tipo,ingredientes,descripcion,alergenos);
                // Agregar el plato a la lista en ListaCarrito
                ListaCarrito.getInstance().agregarPlato(platoACarro);
                // Realiza las acciones correspondientes en este caso

            } else {
                boolean platoRepetido = false;

                for (Plato plato : platos) {
                    if (plato.get_Id().equals(id)) {
                        // El plato ya existe en el carrito
                        Log.d("ListaCarrito", "Plato repetido "+plato.get_Cantidad()+" veces");
                        platoRepetido = true;
                        plato.set_Cantidad(plato.get_Cantidad()+1);
                        Log.d("ListaCarrito", "Añado uno, ahora está repetido "+plato.get_Cantidad()+" veces");
                        break; // Salir del bucle si se encuentra un plato repetido
                    }
                }

                if (!platoRepetido) {
                    Log.d("ListaCarrito", "Se agregó un plato nuevo");
                    // Crear un nuevo objeto Plato si no está en el carrito
                    Plato platoACarro = new Plato(id, nombre, precio,cantidad,tipo,ingredientes,descripcion,alergenos);
                    // Agregar el plato a la lista en ListaCarrito
                    ListaCarrito.getInstance().agregarPlato(platoACarro);
                    // Realizar las acciones correspondientes en este caso
                }
            }

            // Cerrar el diálogo
            dismiss();
        });

        builder.setView(view);
        Dialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    public static PlatoFragment newInstance(String id, String nombre, String precio, int cantidad,String ingredientes,String descripcion,String alergenos) {
        PlatoFragment fragment = new PlatoFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("nombre", nombre);
        args.putString("precio", precio);
        args.putInt("cantidad",cantidad);
        args.putString("ingredientes", ingredientes);
        args.putString("descripcion", descripcion);
        args.putString("alergenos", alergenos);
        fragment.setArguments(args);
        return fragment;
    }

}
