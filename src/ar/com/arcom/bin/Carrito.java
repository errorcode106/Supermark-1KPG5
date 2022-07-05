package ar.com.arcom.bin;

import java.util.ArrayList;

public class Carrito {
    private ArrayList<Articulo> articulos;
    private boolean comprado;

    public Carrito(){
        articulos = new ArrayList<>();
        comprado = false;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public ArrayList<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(ArrayList<Articulo> articulos) {
        this.articulos = articulos;
    }

    public void añadirArticulo(Articulo articulo){
        this.articulos.add(articulo);
    }

    public void eliminar(int id, int cantidad){
        int i = 0;
        while(articulos.get(i).getId() != id) i++;
        if(i<articulos.size()) {
            articulos.get(i).restar(cantidad);
            if(articulos.get(i).getCantidad()<1) articulos.remove(articulos.get(i));
        }
    }

    public void agregaCantidad(int id, int cantidad) {
        int i = 0;
        while (articulos.get(i).getId() != id && i < articulos.size()) i++;
        if(i < articulos.size()) articulos.get(i).añadir(cantidad);
    }
    public boolean verificaCantidad(int id, int cantidad){
        int i = 0;
        while (articulos.get(i).getId() != id && i < articulos.size()) i++;
        if(i < articulos.size()) return cantidad <= articulos.get(i).getCantidad();
        else return false;
    }
    public void quitarCantidad(int id, int cantidad) {
        int i = 0;
        while (articulos.get(i).getId() != id && i < articulos.size()) i++;
        if(i < articulos.size()) {
            articulos.get(i).restar(cantidad);
            if (articulos.get(i).getCantidad()<1) articulos.remove(i);
        }
    }
    public boolean consultaSiExisteID(int id){
        int i = 0;
        while (articulos.get(i).getId() != id && i < articulos.size()) i++;
        return i < articulos.size();
    }
}
