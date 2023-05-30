package com.example.restaurante;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlatoMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//Clase que maneja los platos que aparecen en las distintas listas productos (menús y carro)
public class PlatoMenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlatoMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlatoMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlatoMenuFragment newInstance(String param1, String param2) {
        PlatoMenuFragment fragment = new PlatoMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button botonCarro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_plato_menu, container, false);

        botonCarro=root.findViewById(R.id.botonFragmentPlatoMenu);
        botonCarro.setOnClickListener(view -> {
            String nombre = getArguments().getString("nombre");
            String precio = getArguments().getString("precio");
            String id = getArguments().getString("id");
            int cantidad=getArguments().getInt("cantidad");
            String ingredientes=getArguments().getString("ingredientes");
            String descripcion=getArguments().getString("descripcion");
            String alergenos=getArguments().getString("alergenos");
            PlatoFragment dialogFragment = PlatoFragment.newInstance(id,nombre,precio,cantidad,ingredientes,descripcion,alergenos);
            dialogFragment.show(getFragmentManager(), "plato_dialog");

        });

        return root;
    }

    //Metodo que recoge identifica las vistas y les pasa los argumentos recogidos
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textViewNombre = getView().findViewById(R.id.text1Fragment);
        TextView textViewPrecio = getView().findViewById(R.id.text2Fragment);
        Button button = getView().findViewById(R.id.botonFragmentPlatoMenu);
        TextView textViewDescripcion=getView().findViewById(R.id.textDescripcion);

        textViewNombre.setText(getArguments().getString("nombre"));
        textViewPrecio.setText(getArguments().getString("precio")+" E");
        textViewDescripcion.setText(getArguments().getString("descripcion"));
        //Creo el nombre de la imagen
        String nombreImagen="a"+getArguments().getString("id");
        //Cargo en un Drawable el archivo de imagen
        Drawable imagen = requireContext().getResources().getDrawable(getResources().getIdentifier(nombreImagen, "drawable", requireContext().getPackageName()));
        button.setBackground(imagen);
        button.setText("");
    }
}