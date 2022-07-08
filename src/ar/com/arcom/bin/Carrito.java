package ar.com.arcom.bin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Carrito {
    private ArrayList<Articulo> articulos;
    private boolean comprado;
    private String fechahora;

    public Carrito(){
        articulos = new ArrayList<>();
        comprado = false;
        fechahora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
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
        if (articulos.size()>0) {
            while (i < articulos.size() && articulos.get(i).getId() != id) i++;
        } else i++;
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
        if (articulos.size()>0) {
            while (i < articulos.size() && articulos.get(i).getId() != id) i++;
        } else i++;
        return i < articulos.size();
    }

    public void recalcularTotales() {
        for (Articulo articulo : articulos) articulo.calculaTotal();
    }
    public String getTotales() {
        float total = 0;
        for (Articulo articulo : articulos) total += articulo.getTotal();
        return String.valueOf(total);
    }
    public String getFechahora() {
        return fechahora;
    }

    public Articulo getArticulo(int id) {
        int i = 0;
        if (articulos.size()>0) {
            while (i < articulos.size() && articulos.get(i).getId() != id) i++;
        } else i++;
        return (i < articulos.size()) ? articulos.get(i) : null;
    }
}
