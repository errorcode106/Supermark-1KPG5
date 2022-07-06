package ar.com.arcom.console;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Articulo;
import ar.com.arcom.bin.Cliente;
import ar.com.arcom.handlers.ActionHandler;
import ar.com.arcom.handlers.UIHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientConsole implements UIHelper {
    private Application application;
    private ActionHandler actionHandler;

    public ClientConsole(Application application, ActionHandler actionHandler) {
        this.application = application;
        this.actionHandler = actionHandler;
    }

    // ----------------------------------------------------------------
    // Métodos de flujo
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
    public void menuClienteFlujo(){
        int respuesta;
        do {
            respuesta = menuPrincipalCliente();
            switch (respuesta){
                case 0 -> actionHandler.cerrarSesion();
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
                case 1 -> {
                    int id = ingreseID("agregar");
                    if(id != 0){
                        int cantidad = ingreseCantidad(id, "agregar");
                        if (cantidad != 0) {
                            actionHandler.agregaCarrito(id,cantidad);
                            ((Cliente)(application.getUsuario())).agregar(new Articulo(id,cantidad));
                        }
                    }
                }
                case 2 -> actionHandler.configuraUI(this,3);
                case 3 -> {}
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuVerCarritoFlujo(){
        int respuesta;
        do {
            respuesta = menuVerCarrito();
            switch (respuesta){
                case 1 -> {menuVerProductoFlujo();}
                case 2 -> {menuModificarProductoCarritoFlujo();}
                case 3 -> {menuCompraFlujo();}
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuModificarProductoCarritoFlujo(){
        int respuesta;
        do {
            respuesta = menuModificarProductoDelCarrito();
            switch (respuesta){
                case 1 -> {
                    int id = ingreseID("agregar");
                    if(id != 0){
                        int cantidad = ingreseCantidad(id, "agregar");
                        if (cantidad != 0) {
                            actionHandler.modificarArticuloCarrito(id,cantidad);
                            ((Cliente)(application.getUsuario())).getCarrito().agregaCantidad(id,cantidad);
                        }
                    }
                }
                case 2 -> {
                    int id = ingreseID("quitar");
                    if(id != 0){
                        int cantidad = ingreseCantidad(id, "quitar");
                        if (cantidad != 0) {
                            ((Cliente)(application.getUsuario())).getCarrito().quitarCantidad(id,cantidad);
                            actionHandler.modificarQuitarArticuloCarrito(id,cantidad);
                        }
                    }
                }
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    private void menuCompraFlujo() {
    }

    // ----------------------------------------------------------------
    // Métodos de opciones
    // ----------------------------------------------------------------
    public int menuPrincipalCliente() {
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Principal");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Ver Productos.");
        System.out.println("[2] Ver Carrito.");
        System.out.println("");
        System.out.println("[0] Cerrar Sesion.");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public int menuVerProductos(){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Ver Productos");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");

        List<List<String>> lista = actionHandler.obtenerProductos();

        System.out.println(" ID | Producto | Precio | Existencias |");

        for (List<String> cadena : lista) {
            for (String str : cadena) {
                System.out.print(str); System.out.print(" | ");
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Seleccionar Producto.");
        System.out.println("[2] Buscar Producto.");
        System.out.println("[3] Ver Carrito.");
        System.out.println("");
        System.out.println("[0] Volver.");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public int menuVerCarrito() {
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Ver Carrito");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");
        System.out.println("Lista de productos en el carrito de compras");
        System.out.println(" ID | Producto | Precio | Cantidad | Total |");

        List<List<String>> lista = new ArrayList<>();
        lista = actionHandler.obtenerArticulos(((Cliente)(application.getUsuario())).getCarrito().getArticulos());

        for (List<String> cadena : lista) {
            for (String str : cadena) {
                System.out.print(str); System.out.print(" | ");
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Agregar un Producto.");
        System.out.println("[2] Modificar.");
        System.out.println("[3] Comprar.");
        System.out.println("");
        System.out.println("[0] Volver.");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public int menuModificarProductoDelCarrito(){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Modificar Producto del Carrito");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");

        List<List<String>> listax;
        listax = actionHandler.obtenerArticulos(((Cliente)(application.getUsuario())).getCarrito().getArticulos());

        for (List<String> cadena : listax) {
            for (String str : cadena) {
                System.out.print(str); System.out.print(" | ");
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Agregar");
        System.out.println("[2] Quitar");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scanner();
    }

    private int ingreseID(String modo){
        boolean aux = false;
        int id;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese el ID del producto a " + modo + " o ingrese 0 para cancelar.");
            id = scanner();
            if(verificaID(id, modo)) aux = true;
            if (!aux) {
                System.out.println("----------------------------------------------------");
                System.out.println("-------->El valor ingresado es incorrecto <---------");
            }
        }while (!aux && id != 0 );
        return id;
    }
    private int ingreseCantidad(int id, String cadena){
        boolean aux = false;
        int cantidad;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese la cantidad a " + cadena + " o ingrese 0 para cancelar.");
            cantidad = scanner();
            if(cantidad !=0 && verificaCantidad(id, cantidad, cadena.equalsIgnoreCase("agregar")))
                aux = true;
            if (!aux) {
                System.out.println("----------------------------------------------------");
                System.out.println("-------->El valor ingresado es incorrecto <---------");
            }
        }while (!aux && cantidad != 0);
        return cantidad;
    }

    private boolean verificaCantidad(int id, int cantidad, boolean operacion) {
        if (operacion) return actionHandler.consultaStock(id, cantidad);
        else return ((Cliente)(application.getUsuario())).getCarrito().verificaCantidad(id,cantidad);
    }
    private boolean verificaID(int id, String modo) {
        if (modo.equalsIgnoreCase("agregar")) return actionHandler.consultaSiExiste(id);
        else return ((Cliente)application.getUsuario()).getCarrito().consultaSiExisteID(id);
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
            case 4 -> JOptionPane.showMessageDialog(null, "Usuario ya existe");
            case 5 -> JOptionPane.showMessageDialog(null, "Error al conectar al servidor, intente de nuevo");
            default -> {}
        }
    }
    @Override
    public void cargaLista(int valor) {
        switch (valor){
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
    }
    @Override
    public void ultimosArreglos(int valor) {
        switch (valor){
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
    }
}
