package com.example.restaurante;

import java.util.ArrayList;
import java.util.List;

public class ListaCarrito {
    private static ListaCarrito instance;
    private List<Plato> platos;

    private ListaCarrito() {
        platos = new ArrayList<>();
    }

    public static synchronized ListaCarrito getInstance() {
        if (instance == null) {
            instance = new ListaCarrito();
        }
        return instance;
    }

    public List<Plato> getPlatos() {
        return platos;
    }

    public void agregarPlato(Plato plato) {
        platos.add(plato);
    }

    public void eliminarPlato(Plato plato) {
        platos.remove(plato);
    }

    /* PARA AGREGAR PLATOS A LA LISTA
    Plato plato = new Plato("1", "Nombre del plato", "Precio del plato");
    ListaCarrito.getInstance().agregarPlato(plato);*/
}
