package ar.com.arcom.bin;

public class Articulo {
    private int id;
    private String nombre;
    private String descripcion;
    private float precio;
    private int cantidad;
    private float total;

    public Articulo(int id, String nombre, String descripcion, float precio, int cantidad, float total) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = total;
    }
    public Articulo() {
        id = 0;
        nombre = "";
        descripcion = "";
        precio = 0f;
        cantidad = 0;
        total = 0f;
    }

    public void set(String label, String labelData){
        switch (label){
            case "id" -> id = Integer.parseInt(labelData);
            case "nombre" -> nombre = labelData;
            case "descripcion" -> descripcion = labelData;
            case "precio" -> precio = Float.parseFloat(labelData);
            case "cantidad" -> cantidad = Integer.parseInt(labelData);
            default -> {}
        }
    }
    public void calculaTotal() {
        total = cantidad * precio;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public float getPrecio() {
        return precio;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public float getTotal() {
        return total;
    }

    public void añadir(int cantidad){
        this.cantidad += cantidad;
    }
    public void restar(int cantidad){
        this.cantidad -= cantidad;
    }
}
