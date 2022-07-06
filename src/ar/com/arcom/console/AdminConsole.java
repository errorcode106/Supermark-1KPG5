package ar.com.arcom.console;

import ar.com.arcom.Application;
import ar.com.arcom.handlers.ActionHandler;

import java.util.List;
import java.util.Scanner;

public class AdminConsole {
    private Application application;
    private ActionHandler actionHandler;

    public AdminConsole(Application application, ActionHandler actionHandler) {
        this.application = application;
        this.actionHandler = actionHandler;
    }

    // ----------------------------------------------------------------
    // Métodos de flujo
    // ----------------------------------------------------------------
    public void menuPrincipalFlujo(){
        int respuesta;
        do {
            respuesta = menuPrincipal();
            switch (respuesta){
                case 0 -> {}
                case 1 -> {}
                case 2 -> {}
                case 3 -> {}
                case 4 -> {}
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuVerProductoFlujo(){
        int respuesta;
        do {
            respuesta = menuVerProductos();
            switch (respuesta){
                case 1 -> {}
                case 2 -> {}
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }

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

    // ----------------------------------------------------------------
    // Métodos de opciones
    // ----------------------------------------------------------------
    public int menuPrincipal() {
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Principal (Administrador)");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Ver listado de productos");
        System.out.println("[2] Modificar datos de los productos");
        System.out.println("[3] Ver todos los usuarios que realizaron una compra");
        System.out.println("[4] Ver listado de productos seleccionados por el usuario");
        System.out.println("");
        System.out.println("[0] Cerrar Sesion");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public int menuVerProductos(){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Ver Productos (Administrador)");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");

        List<List<String>> lista = actionHandler.obtenerProductos();

        System.out.println(" ID | Producto | Precio | stock |");

        for (List<String> cadena : lista) {
            for (String str : cadena) {
                System.out.print(" " + str); System.out.print(" | ");
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Modificar Producto");
        System.out.println("[2] Cargar producto");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
}
