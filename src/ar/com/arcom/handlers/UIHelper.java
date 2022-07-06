package ar.com.arcom.handlers;

public interface UIHelper {
    public final static int LISTA_PRODUCTOS = 0, LISTA_CARRITO_CLIENTE = 1, LISTA_DE_CLIENTES = 2;

    public void configuraUI(int valor);
    public void cargaLista(int valor);
    public void ultimosArreglos(int valor);
}
