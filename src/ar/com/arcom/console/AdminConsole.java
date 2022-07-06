package ar.com.arcom.console;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Cliente;
import ar.com.arcom.handlers.ActionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdminConsole {
    // ----------------------------------------------------------------
    // Atributos
    // ----------------------------------------------------------------
    public final static int LISTA_PRODUCTOS = 0;

    private Application application;
    private ActionHandler actionHandler;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
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
        tituloMenu("Menu Ver Productos (Administrador)");
        cargaLista(LISTA_PRODUCTOS);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Modificar Producto");
        System.out.println("[2] Cargar producto");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scanner();
    }

    private void tituloMenu(String cadena){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO + " : " + cadena);
        System.out.println("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0)
                + application.getUsuario().getNombre().substring(1));
        System.out.println("----------------------------------------------------");
    }
    private void tablaFix(String[] etiquetas, List<List<String>> lista){
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
    }
    private void cargaLista(int valor) {
        switch (valor){
            case 0 -> {
                System.out.println("Lista de productos disponibles");
                tablaFix(new String[]{"ID","Producto","Descripcion","Precio","Existencias"},
                        actionHandler.obtenerProductos(true));
                System.out.println("----------------------------------------------------");
            }
            case 1 -> {
                System.out.println("Lista de productos en el carrito de compras");
                tablaFix(new String[]{"ID","Producto","Descripcion","Precio","Cantidad", "Total"},
                        actionHandler.obtenerArticulos(((Cliente)(application.getUsuario())).getCarrito().getArticulos()));
                System.out.println("----------------------------------------------------");
            }
            default -> {
                System.out.println("ERROR: No hay listas para mostrar.");
                System.out.println("----------------------------------------------------");
            }
        }
    }
}
