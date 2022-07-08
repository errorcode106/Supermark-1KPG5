package ar.com.arcom.console;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Cliente;
import ar.com.arcom.handlers.ActionHandler;
import ar.com.arcom.handlers.EndUp;
import ar.com.arcom.handlers.UIHelper;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientConsole implements UIHelper, EndUp {
    public final static int LISTA_PRODUCTOS = 0, LISTA_CARRITO_CLIENTE = 1;
    // ----------------------------------------------------------------
    // Atributos
    // ----------------------------------------------------------------
    private Application application;
    private ActionHandler actionHandler;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public ClientConsole(Application application, ActionHandler actionHandler) {
        this.application = application;
        this.actionHandler = actionHandler;
    }

    // ----------------------------------------------------------------
    // Métodos de flujo
    // ----------------------------------------------------------------

    public void menuClienteFlujo(){
        int respuesta;
        do {
            respuesta = menuPrincipalCliente();
            switch (respuesta){
                case 0 -> actionHandler.cerrarSesion(this);
                case 1 -> actionHandler.configuraUI(this,2);
                case 2 -> actionHandler.configuraUI(this,3);
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuVerProductoFlujo(){
        int respuesta;
        do {
            respuesta = menuVerProductos();
            switch (respuesta){
                case 1 -> actionHandler.agregaAlCarrito(this);
                case 2 -> JOptionPane.showMessageDialog(null,
                        (actionHandler.buscarProductoNombre(this)!=0) ? "El producto tiene indice: " + actionHandler.buscarProductoNombre(this) : "Producto no encontrado!");
                case 3 -> actionHandler.configuraUI(this,3);
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuVerCarritoFlujo(){
        int respuesta;
        do {
            respuesta = menuVerCarrito();
            switch (respuesta){
                case 1 -> actionHandler.configuraUI(this,2);
                case 2 -> actionHandler.configuraUI(this,4);
                case 3 -> actionHandler.vaciarCarrito();
                case 4 -> actionHandler.configuraUI(this,5);
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuModificarProductoCarritoFlujo(){
        int respuesta;
        do {
            respuesta = menuModificarProductoDelCarrito();
            switch (respuesta){
                case 1 -> actionHandler.agregarCantidadProducto(this,false);
                case 2 -> actionHandler.quitarCantidadProducto(this);
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuCompraFlujo(){
        int respuesta;
        do {
            respuesta = menuCompra();
            switch (respuesta){
                case 1 -> actionHandler.comprar(this);
                //case 2 -> actionHandler.configuraUI(this,3);
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }

    // ----------------------------------------------------------------
    // Métodos de opciones
    // ----------------------------------------------------------------
    public int menuPrincipalCliente() {
        tituloMenu("Menu Principal");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Ver Productos.");
        System.out.println("[2] Ver Carrito.");
        System.out.println("");
        System.out.println("[0] Cerrar Sesion.");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public int menuVerProductos(){
        tituloMenu("Menu Ver Productos");
        cargaLista(LISTA_PRODUCTOS);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Agregar al carrito.");
        System.out.println("[2] Buscar Producto.");
        System.out.println("[3] Ver Carrito.");
        System.out.println("");
        System.out.println("[0] Volver.");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public int menuVerCarrito() {
        tituloMenu("Menu Ver Carrito");
        cargaLista(LISTA_CARRITO_CLIENTE);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Agregar un Producto.");
        System.out.println("[2] Modificar.");
        System.out.println("[3] Vaciar Carrito.");
        System.out.println("[4] Comprar.");
        System.out.println("");
        System.out.println("[0] Volver.");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public int menuModificarProductoDelCarrito(){
        tituloMenu("Modificar Producto del Carrito");
        cargaLista(LISTA_CARRITO_CLIENTE);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Agregar");
        System.out.println("[2] Quitar");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    private int menuCompra() {
        tituloMenu("Menu Compra");
        cargaLista(LISTA_CARRITO_CLIENTE);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Finalizar Compra.");
        System.out.println("");
        System.out.println("[0] Volver.");
        System.out.println("----------------------------------------------------");
        return scanner();
    }

    private boolean verificaCantidad(int id, int cantidad, boolean operacion) {
        if (operacion) return actionHandler.consultaStock(id, cantidad);
        else return ((Cliente)(application.getUsuario())).getCarrito().verificaCantidad(id,cantidad);
    }
    private boolean verificaID(int id, boolean aDeBaseDatos) {
        if (aDeBaseDatos) return actionHandler.consultaSiExiste(id);
        else return ((Cliente) application.getUsuario()).getCarrito().consultaSiExisteID(id);
    }

    // ----------------------------------------------------------------
    // Métodos generales
    // ----------------------------------------------------------------
    private int scanner(){
        String valor = "";
        int i;
        i = 0;
        System.out.print("Respuesta: ");
        Scanner scanner = new Scanner(System.in);
        valor = scanner.next();
        while(i < valor.length() && Character.isDigit(valor.charAt(i))) i++;
        if (i < valor.length()) valor ="-1";
        return Integer.parseInt(valor);
    }
    private void tituloMenu(String cadena){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : " + cadena);
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");
    }
    private void tablaFix(String[] etiquetas, List<List<String>> lista, boolean necesitaTotal){
        String[] separador = new String[etiquetas.length];
        Arrays.fill(separador, "");
        int[] mayorPeso = new int[etiquetas.length];
        for (int i = 0; i < mayorPeso.length; i++) mayorPeso[i] = etiquetas[i].length();

        for (List<String> strings : lista) {
            for (int j = 0; j < strings.size(); j++) {
                if (strings.get(j).length() > mayorPeso[j]) mayorPeso[j] = strings.get(j).length()-1;
            }
        }

        for (int i = 0; i < mayorPeso.length; i++) {
            for (int j = 0; j < mayorPeso[i] - etiquetas[i].length(); j++) {
                separador[i] += " ";
            }
        }

        for (int i = 0; i < etiquetas.length; i++) {
            System.out.print(etiquetas[i]+separador[i]+ " | ");
        }
        System.out.println("");
        System.out.println("----------------------------------------------------");

        for (List<String> cadena : lista) {
            Arrays.fill(separador, "");
            for (int i = 0; i < separador.length; i++)
                for (int j = 0; j < (mayorPeso[i] - cadena.get(i).length()); j++)
                    separador[i] += " ";

            for (int i = 0; i < cadena.size(); i++) {
                System.out.print(cadena.get(i) + separador[i] + " | ");
            }
            System.out.println();
        }

        if (necesitaTotal) {
            if (((Cliente)(application.getUsuario())).getCarrito().getArticulos() != null){
                int aux = 0;
                for (int i = 0; i < mayorPeso.length; i++) aux += mayorPeso[i]+3;
                aux -= (3 + 7 + String.valueOf(((Cliente)(application.getUsuario())).getCarrito().getTotales()).length());

                Arrays.fill(separador, "");
                for (int i = 0; i < aux; i++) separador[0] += " ";

                System.out.println(separador[0] + "Total: "
                        + ((Cliente)(application.getUsuario())).getCarrito().getTotales());
            } else {
                System.out.println(separador[0] + "Total: "
                        + 0.0);
            }
        }
    }
    private void cargaLista(int valor) {
        switch (valor){
            case 0 -> {
                System.out.println("Lista de productos disponibles");
                tablaFix(new String[]{"ID","Producto","Descripcion","Precio","Existencias"},
                        actionHandler.obtenerProductos(true),false);
                System.out.println("----------------------------------------------------");
            }
            case 1 -> {
                System.out.println("Lista de productos en el carrito de compras");
                tablaFix(new String[]{"ID","Producto","Descripcion","Precio","Cantidad", "Sub Total"},
                        actionHandler.obtenerArticulos(((Cliente)(application.getUsuario())).getCarrito().getArticulos()),
                true);
                System.out.println("----------------------------------------------------");
            }
            default -> {
                System.out.println("ERROR: No hay listas para mostrar.");
                System.out.println("----------------------------------------------------");
            }
        }
    }
    // ----------------------------------------------------------------
    // Métodos de implementacion
    // ----------------------------------------------------------------
    @Override
    public void configuraUI(int valor) {
        switch (valor){
            case 1 -> menuClienteFlujo();
            case 2 -> menuVerProductoFlujo();
            case 3 -> menuVerCarritoFlujo();
            case 4 -> menuModificarProductoCarritoFlujo();
            case 5 -> menuCompraFlujo();
            default -> {}
        }
    }
    @Override
    public int getID(String valor, boolean aDeBaseDatos) {
        boolean aux = false;
        int id = -1;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese el ID del producto a " + valor + " o ingrese 0 para cancelar.");

            String str;
            int i;
            i = 0;
            Scanner scanner = new Scanner(System.in);
            str = scanner.next();
            while(i < str.length() && Character.isDigit(str.charAt(i))) i++;
            if (i < str.length()) {
                str ="-1";
                System.out.println("----------------------------------------------------");
                System.out.println("-------->El valor ingresado es incorrecto <---------");
            }
            else {
                id = Integer.parseInt(str);
                if(verificaID(id, aDeBaseDatos)) aux = true;
                if (!aux) {
                    System.out.println("----------------------------------------------------");
                    System.out.println("-------->El valor ingresado es incorrecto <---------");
                }
            }
        }while (!aux && id != 0 );

        return id;
    }
    @Override
    public int getCantidad(int id, String valor) {
        boolean aux = false;
        int cantidad;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese la cantidad a " + valor + " o ingrese 0 para cancelar.");
            cantidad = scanner();
            if(cantidad !=0 && verificaCantidad(id, cantidad, valor.equalsIgnoreCase("agregar")))
                aux = true;
            if (!aux) {
                System.out.println("----------------------------------------------------");
                System.out.println("-------->El valor ingresado es incorrecto <---------");
            }
        }while (!aux && cantidad != 0);
        return cantidad;
    }
    @Override
    public void error(int valor) {
        switch (valor){
            case 0 -> {
                System.out.println("ERROR: No es posible agregar el producto seleccionado");
                System.out.println("por falta de stock.");
                System.out.println("----------------------------------------------------");
            }
            case 1 -> {
                System.out.println("ERROR: El Articulo seleccionado no es correcto.");
                System.out.println("----------------------------------------------------");
            }
            case 2 -> {
                System.out.println("ERROR: No se pudo realizar la compra.");
                System.out.println("----------------------------------------------------");
            }
            default -> {
                System.out.println("ERROR: No especificado.");
                System.out.println("----------------------------------------------------");
            }
        }
    }
    @Override
    public String getNombre() {
        String nombre;
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese el nombre del producto.");

        Scanner scanner = new Scanner(System.in);
        nombre = scanner.nextLine();
        return nombre;
    }
    @Override
    public String getDescripcion() {
        return null;
    }
    @Override
    public float getPrecio() {
        return 0;
    }
    @Override
    public int getStock() {
        return 0;
    }
    @Override
    public void endUp(int valor) {
    }
}
