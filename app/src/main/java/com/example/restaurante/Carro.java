package com.example.restaurante;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Carro extends AppCompatActivity implements PlatoDeCarro.DialogListener {

    Button botonVolver,botonIdiomas,botonPago,botonLlamarCamareroCarro;

    TextView totalFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);

        botonLlamarCamareroCarro=findViewById(R.id.botonLlamarCamareroCarro);
        botonVolver=findViewById(R.id.carroVolverBtn);
        botonIdiomas=findViewById(R.id.idiomasBtn);
        botonPago=findViewById(R.id.botonPagarAqui);
        totalFinal=findViewById(R.id.totalFinal);

        botonPago.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Carro.this);
            builder.setTitle("¿Estás seguro?")
                    .setMessage("¿Quiere lanzar el pago?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acción cuando se selecciona "Sí"
                            Log.d("Simulación de Pago", "El pago se realizó con éxito");

                            // Mostrar un mensaje en un Toast como simulación del pago exitoso
                            Toast.makeText(getApplicationContext(), "El pago se realizó con éxito", Toast.LENGTH_LONG).show();
                            //Accedo a la clase listacarrito
                            ListaCarrito listaCarrito = ListaCarrito.getInstance();
                            // Accede a la lista de platos y elimina cada elemento
                            List<Plato> platos = listaCarrito.getPlatos();
                            platos.clear(); // Vacía la lista eliminando todos los elementos
                            reiniciarCarro();// Reinicio el carro
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        });

        botonLlamarCamareroCarro.setOnClickListener(v -> {
            llamarCamarero();
        });

        botonVolver.setOnClickListener(v -> {
            Intent i=new Intent(Carro.this,Principal.class);
            startActivity(i);
        });

        botonIdiomas.setOnClickListener(v -> {
            Intent i=new Intent(Carro.this,MainActivity.class);
            startActivity(i);
        });

        rellenarCarro();
        recalcularTotal();

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void mostrarPlato(View view){
        PlatoFragment popupFragment = new PlatoFragment();
        popupFragment.show(getSupportFragmentManager(), "popup");
    }

    //Método que lee la listaCarrito de pasa los arguments para que se creen los fragment PlatoCarroFragment
    public void rellenarCarro()
    {
        for (Plato plato : ListaCarrito.getInstance().getPlatos()) {
            PlatoCarroFragment fragment = new PlatoCarroFragment();
            // Crea un Bundle para pasar los valores al fragmento
            Bundle args = new Bundle();
            args.putString("id", plato.get_Id());
            args.putString("nombre", plato.get_Nombre());
            args.putString("precio", plato.get_Precio());
            args.putInt("cantidad",plato.get_Cantidad());
            args.putString("ingredientes", plato.get_Ingredientes());
            args.putString("descripcion", plato.get_Descripcion());
            args.putString("alergenos", plato.get_Alergenos());
            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.listaPlatosCarro, fragment);
            fragmentTransaction.commit();
        }
    }

    //método que vacía el carro completamente
    public void eliminarFragmentosCarro() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Obtiene la lista de fragmentos en el contenedor listaPlatosCarro
        List<Fragment> fragmentos = fragmentManager.getFragments();
        if (fragmentos != null) {
            for (Fragment fragmento : fragmentos) {
                fragmentManager.beginTransaction().remove(fragmento).commit();
            }
        }
    }

    @Override
    public void onDialogClosed() {
        Log.d("consola","se ha cerrado una ventana");
        reiniciarCarro();
    }

    public void reiniciarCarro()
    {
        Log.d("carro","se ha reiniciado el carro");
        eliminarFragmentosCarro();
        rellenarCarro();
        recalcularTotal();
    }

    public void recalcularTotal()
    {
        int sumandoTotales=0;
        for (Plato plato : ListaCarrito.getInstance().getPlatos()) {
            Log.d("carro","Calculando totales, vamos por "+sumandoTotales);
            sumandoTotales=sumandoTotales+(plato.get_Cantidad()*Integer.parseInt(plato.get_Precio()));
            Log.d("carro","Calculando totales, vamos por "+sumandoTotales);
        }
        totalFinal.setText(""+sumandoTotales+" Euros");
    }

    public void llamarCamarero()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Carro.this);
        builder.setTitle("¿LLamar al camarero?")
                .setMessage("¿Quiere llamar al camarero?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Mostrar un mensaje en un Toast como una llamada
                    Toast.makeText(getApplicationContext(), "Se ha llamado al camarero, por favor espere", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("No", (dialog, which) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}