package ar.com.arcom.handlers;

public interface UIHelper {
    public void configuraUI(int valor);
    public int getID(String valor, boolean aDeBaseDatos);
    public int getCantidad(int id, String valor);
    public void error(int valor);

    public String getNombre();
    public String getDescipcion();
    public float getPrecio();
    public int getStock();
}
