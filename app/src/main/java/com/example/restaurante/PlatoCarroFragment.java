package com.example.restaurante;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlatoCarroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlatoCarroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PlatoCarroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlatoCarroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlatoCarroFragment newInstance(String param1, String param2) {
        PlatoCarroFragment fragment = new PlatoCarroFragment();
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

    private Button botonCarrito, botonMas, botonMenos;

    private TextView textTotal,cantCarro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_plato_carro, container, false);

        textTotal=root.findViewById(R.id.textTotal);
        cantCarro=root.findViewById(R.id.cantidadCarro);

        String precioInicio = getArguments().getString("precio");
        int cantidadInicio=getArguments().getInt("cantidad");
        int totalPlato=Integer.parseInt(precioInicio)*cantidadInicio;
        textTotal.setText("Precio: "+totalPlato);

        botonMas=root.findViewById(R.id.botonMasCarro);
        botonMas.setOnClickListener(view -> {
            ListaCarrito listaCarrito = ListaCarrito.getInstance();
            List<Plato> platos = listaCarrito.getPlatos();
            String id=getArguments().getString("id");
            for (Plato plato : platos) {
                if (plato.get_Id().equals(id)) {
                    // El plato ya existe en el carrito
                    Log.d("ListaCarrito", "Plato repetido "+plato.get_Cantidad()+" veces");
                    plato.set_Cantidad(plato.get_Cantidad()+1);
                    Log.d("ListaCarrito", "Añado uno, ahora está repetido "+plato.get_Cantidad()+" veces");
                    String precioTemp = plato.get_Precio();
                    int cantidadTemp=plato.get_Cantidad();
                    int totalPLato=Integer.parseInt(precioTemp)*cantidadTemp;
                    textTotal.setText("Precio: "+totalPLato);
                    cantCarro.setText(""+cantidadTemp);
                    Log.d("actualizado", "Precio de plato actualizado CANTIDAD: "+ cantidadTemp +" TOTAL: "+totalPLato);
                    Carro actividad = (Carro) getActivity();
                    actividad.reiniciarCarro();
                    break; // Salir del bucle si se encuentra un plato repetido
                }
            }
        });


        botonMenos=root.findViewById(R.id.botonMenosCarro);
        botonMenos.setOnClickListener(view -> {
            ListaCarrito listaCarrito = ListaCarrito.getInstance();
            List<Plato> platos = listaCarrito.getPlatos();
            String id=getArguments().getString("id");
            for (Plato plato : platos) {
                if (plato.get_Id().equals(id)) {
                    if(plato.get_Cantidad()<=1)
                    {
                        Iterator<Plato> iterator = platos.iterator();
                        while (iterator.hasNext()) {
                            plato = iterator.next();
                            if (plato.get_Id().equals(id)) {
                                iterator.remove(); // Elimina el plato de la lista carro
                                // Ejecutar el método del activity desde este fragmento
                                if (getActivity() instanceof Carro) {
                                    Carro actividad = (Carro) getActivity();
                                    actividad.reiniciarCarro();
                                }
                                break;
                            }
                        }
                }else{
                    // El plato ya existe en el carrito
                    Log.d("ListaCarrito", "Plato repetido "+plato.get_Cantidad()+" veces");
                    plato.set_Cantidad(plato.get_Cantidad()-1);
                    Log.d("ListaCarrito", "Elimino uno, ahora está repetido "+plato.get_Cantidad()+" veces");
                    String precioTemp = plato.get_Precio();
                    int cantidadTemp=plato.get_Cantidad();
                    int totalPLato=Integer.parseInt(precioTemp)*cantidadTemp;
                    textTotal.setText("Precio: "+totalPLato);
                    cantCarro.setText(""+cantidadTemp);
                    Log.d("actualizado", "Precio de plato actualizado CANTIDAD: "+ cantidadTemp +" TOTAL: "+totalPLato);
                    Carro actividad = (Carro) getActivity();
                    actividad.reiniciarCarro();
                    break; // Salir del bucle si se encuentra un plato repetido
                }
                }
            }
        });


        botonCarrito=root.findViewById(R.id.botonFragmentPlatoCarro);
        botonCarrito.setOnClickListener(view -> {
            String nombre = getArguments().getString("nombre");
            String precio = getArguments().getString("precio");
            String id = getArguments().getString("id");
            int cantidad=getArguments().getInt("cantidad");
            String ingredientes=getArguments().getString("ingredientes");
            String descripcion=getArguments().getString("descripcion");
            String alergenos=getArguments().getString("alergenos");


            PlatoDeCarro dialogFragment = PlatoDeCarro.newInstance(id,nombre, precio,cantidad,ingredientes,descripcion,alergenos);
            dialogFragment.show(getFragmentManager(), "plato_dialog");
        });

        return root;
    }

    //Metodo que recoge identifica las vistas y les pasa los argumentos recogidos
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textViewNombre = getView().findViewById(R.id.text1FragmentCarro);
        TextView textViewPrecio = getView().findViewById(R.id.text2FragmentCarro);
        Button button = getView().findViewById(R.id.botonFragmentPlatoCarro);
        TextView cantidadCarro=getView().findViewById(R.id.cantidadCarro);


        textViewNombre.setText(getArguments().getString("nombre"));
        textViewPrecio.setText(getArguments().getString("precio")+" E");
        cantidadCarro.setText("" + getArguments().getInt("cantidad"));
        //Creo el nombre de la imagen
        String nombreImagen="a"+getArguments().getString("id");
        //Cargo en un Drawable el archivo de imagen
        Drawable imagen = requireContext().getResources().getDrawable(getResources().getIdentifier(nombreImagen, "drawable", requireContext().getPackageName()));
        button.setBackground(imagen);
        button.setText("");
    }
}