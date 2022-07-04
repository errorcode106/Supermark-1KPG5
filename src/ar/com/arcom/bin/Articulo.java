package ar.com.arcom.bin;

public class Articulo {
    private int id;
    private int cantidad;

    public Articulo(int id, int cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void añadir(int cantidad){
        this.cantidad += cantidad;
    }

    public void restar(int cantidad){
        this.cantidad -= cantidad;
    }
}
