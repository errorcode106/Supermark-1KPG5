package ar.com.arcom.bin;

import java.util.ArrayList;

public class Cliente extends Usuario{
    private Carrito carrito;

    public Cliente(String usuario) {
        super(usuario, 0);
        carrito = new Carrito();
    }
    public void agregar(Articulo articulo){
        carrito.añadirArticulo(articulo);
    }

    public Carrito getCarrito() {
        return carrito;
    }
}
