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

    private int scanner(){
        System.out.print("Respuesta: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
// ----------------------------------------------------------------
    // Menu Administrador

    public void menuAdministrador(){
        int respuesta;
        do {
            respuesta = menuPrincipalAdministrador();
            switch (respuesta){
                case 0 -> {
                    actionHandler.cerrarSesion();
                }
                case 1 -> {menuVerProductoAdministrador();}
                case 2 -> {}
                case 3 -> System.out.println("administrador");
                default -> {}
            }
        }while (respuesta != 0);
    }
    public int menuPrincipalAdministrador() {
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Principal");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Modificar datos de los productos");
        System.out.println("[2] Modificar datos de los productos");
        System.out.println("");
        System.out.println("[0] Cerrar Sesion");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public void menuVerProductoAdministrador(){
        int respuesta;
        do {
            respuesta = menuProductosAdministrador();
            switch (respuesta){
                case 1 -> {}
                case 2 -> {}
                default -> {}
            }
        }while (respuesta != 0);
    }
    public int menuProductosAdministrador(){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : Menu Ver Productos");
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");

        List<List<String>> lista = actionHandler.obtenerProductos();

        System.out.println(" ID | Producto | Precio | Cantidad | Total |");

        for (List<String> cadena : lista) {
            for (String str : cadena) {
                System.out.print(str); System.out.print(" | ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Modificar Producto");
        System.out.println("[2] Cargar producto");
        System.out.println("[3] Buscar Producto");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scanner();
    }

}
