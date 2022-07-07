package ar.com.arcom.bin;

public class Producto {
    // Atributos
    private int id;
    private String nombre;
    private String descripcion;
    private float precio;
    private int stock;

    // Contructor
    public Producto(int id, String nombre, String descripcion, float precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    // Métodos
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public boolean añadirStock(int ingreso) {
        boolean ingresoCorrecto=true;
        if (ingreso>0) {
            this.stock= this.stock + ingreso;
        }else {
            ingresoCorrecto= false;

        }
        return ingresoCorrecto;
    }

    public boolean validarStock() {
        boolean resultado = false;
        if(this.stock>0) {
            resultado = true;
        }
        return resultado;
    }
    public void quitarStock(int unidades) {
        if(validarStock()) {
            this.stock -= unidades;
        }else {
            System.out.println("stock insuficiente");
        }
    }
}