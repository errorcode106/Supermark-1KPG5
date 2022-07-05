package ar.com.arcom.console;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Articulo;
import ar.com.arcom.bin.Cliente;
import ar.com.arcom.handlers.ActionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientConsole {
    private Application application;
    private ActionHandler actionHandler;

    public ClientConsole(Application application, ActionHandler actionHandler) {
        this.application = application;
        this.actionHandler = actionHandler;
    }

    // ----------------------------------------------------------------
    // Métodos de flujo
    private int scanner(){
        System.out.print("Respuesta: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    public void menuCliente(){
        int respuesta;
        do {
            respuesta = menuPrincipalCliente();
            switch (respuesta){
                case 0 -> {
                    actionHandler.cerrarSesion();
                }
                case 1 -> {menuVerProductoCliente();}
                case 2 -> {menuCarritoCliente();}
                default -> {}
            }
        }while (respuesta != 0);
    }
    public void menuVerProductoCliente(){
        int respuesta;
        do {
            respuesta = menuProductosCliente();
            switch (respuesta){
                case 1 -> {
                    int id = ingreseID();
                    if(id != 0){
                        int cantidad = ingreseCantidadAgregar(id);
                        if (cantidad != 0) {
                            actionHandler.agregaCarrito(id,cantidad);
                            ((Cliente)(application.getUsuario())).agregar(new Articulo(id,cantidad));
                        }
                    }
                }
                case 2 -> {menuVerCarritoCliente();}
                default -> {}
            }
        }while (respuesta != 0);
    }
    public void menuVerCarritoCliente(){
        int respuesta;
        do {
            respuesta = menuCarritoCliente();
            switch (respuesta){
                case 1 -> {
                    menuVerProductoCliente();
                }
                case 2 -> {
                    menuModificarProductoCarritoFlujo();
                }
                default -> {}
            }
        }while (respuesta != 0);
    }
    public void menuModificarProductoCarritoFlujo(){
        int respuesta;
        do {
            respuesta = menuModificarProductoDelCarrito();
            switch (respuesta){
                case 1 -> {
                    int id = ingreseID();
                    if(id != 0){
                        int cantidad = ingreseCantidadAgregar(id);
                        if (cantidad != 0) {
                            actionHandler.modificarArticuloCarrito(id,cantidad);
                            ((Cliente)(application.getUsuario())).getCarrito().agregaCantidad(id,cantidad);
                        }
                    }
                }
                case 2 -> {
                    int id = ingreseID();
                    if(id != 0){
                        int cantidad = ingreseCantidadQuitar(id);
                        if (cantidad != 0) {
                            ((Cliente)(application.getUsuario())).getCarrito().quitarCantidad(id,cantidad);
                            actionHandler.modificarQuitarArticuloCarrito(id,cantidad);
                        }
                    }
                }
                default -> {}
            }
        }while (respuesta != 0);
    }

    // ----------------------------------------------------------------
    // Métodos de opciones
    public int menuPrincipalCliente() {
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Principal");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Ver productos");
        System.out.println("[2] Ver carrito");
        System.out.println("");
        System.out.println("[0] Cerrar Sesion");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public int menuCarritoCliente() {
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
        System.out.println("[1] Agregar un Producto");
        System.out.println("[2] Modificar");
        System.out.println("[3] Comprar");
        System.out.println("");
        System.out.println("[0] Volver");
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


    public int menuProductosCliente(){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Ver Productos");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");

        List<List<String>> lista = actionHandler.obtenerProductos();

        /*int[] mayor = {0,0,0,0,0};
        int aux=0;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.get(i).size(); j++) {
                aux = lista.get(i).get(j).length();
                if (mayor[i]<aux) mayor[i]=aux;
            }
        }
        */

        System.out.println(" ID | Producto | Precio | Cantidad | Total |");

        for (List<String> cadena : lista) {
            for (String str : cadena) {
                System.out.print(str); System.out.print(" | ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Seleccionar Producto");
        System.out.println("[2] Ver Carrito");
        System.out.println("[3] Buscar Producto");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scanner();
    }

    private int ingreseID(){
        boolean aux = false;
        int id;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese el ID de producto o ingrese 0 para cancelar.");
            id = scanner();
            if(verificaID(id)) aux = true;
        }while (!aux && id != 0 );
        return id;
    }

    private int ingreseCantidadAgregar(int id){
        boolean aux = false;
        int cantidad;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese la cantidad o ingrese 0 para cancelar.");
            cantidad = scanner();
            if(cantidad !=0 && verificaCantidadAgregar(id, cantidad)) aux = true;
        }while (!aux && cantidad != 0);
        return cantidad;
    }
    private int ingreseCantidadQuitar(int id) {
        boolean aux = false;
        int cantidad;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese la cantidad o ingrese 0 para cancelar.");
            cantidad = scanner();
            if(cantidad !=0 && verificaCantidadQuitar(id, cantidad)) aux = true;
        }while (!aux && cantidad != 0);
        return cantidad;
    }

    private boolean verificaCantidadQuitar(int id, int cantidad) {
        return ((Cliente)(application.getUsuario())).getCarrito().verificaCantidad(id,cantidad);
    }

    private boolean verificaCantidadAgregar(int id, int cantidad) {
        return actionHandler.consultaStock(id, cantidad);
    }
    private boolean verificaID(int valor) {
        return true;
    }

    // ----------------------------------------------------------------
    // Métodos de implementacion
}
