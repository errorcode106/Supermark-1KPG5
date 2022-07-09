package ar.com.arcom.console;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Cliente;
import ar.com.arcom.handlers.ActionHandler;
import ar.com.arcom.handlers.EndUp;
import ar.com.arcom.handlers.UIHelper;
import ar.com.arcom.mysql.MySQLHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdminConsole implements UIHelper, EndUp {
    // ----------------------------------------------------------------
    // Atributos
    // ----------------------------------------------------------------
    public final static int LISTA_PRODUCTOS = 0, LISTA_USUARIOS = 1, LISTA_PEDIDOS = 2, LISTA_ORDENES = 3;

    private Application application;
    private ActionHandler actionHandler;
    private int id, id_pedido;
    private String fecha;
    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public AdminConsole(Application application, ActionHandler actionHandler) {
        this.application = application;
        this.actionHandler = actionHandler;
        id = 0;
        id_pedido = 0;
        fecha = null;
    }

    // ----------------------------------------------------------------
    // Métodos de flujo
    // ----------------------------------------------------------------
    public void menuPrincipalFlujo(){
        int respuesta;
        do {
            respuesta = menuPrincipal();
            switch (respuesta){
                case 0 -> actionHandler.cerrarSesion(this);
                case 1 -> actionHandler.configuraUI(this,7);
                case 2 -> actionHandler.configuraUI(this,8);
                case 3 -> actionHandler.configuraUI(this,9);
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuVerProductoFlujo(){
        int respuesta;
        do {
            respuesta = menuVerProductos();
            switch (respuesta){
                case 1 -> actionHandler.configuraUI(this,8);
                case 2 -> actionHandler.cargarProducto(this);
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuModificalProductoFlujo(){
        int respuesta;
        do {
            respuesta = menuModificarProducto();
            switch (respuesta){
                case 1 -> actionHandler.agregarCantidadProducto(this,true);
                case 2 -> actionHandler.quitarCantidadProducto(this);
                case 3 -> actionHandler.modificaNombre(this);
                case 4 -> actionHandler.modificaDescripcion(this);
                case 5 -> actionHandler.modificaPrecio(this);

                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuVerUsuariosFlujo(){
        int respuesta;
        do {
            respuesta = menuVerUsuarios();
            switch (respuesta){
                case 1 -> verPedidos();
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuVerPedidosFlujo(){
        int respuesta;
        do {
            respuesta = menuVerPedidos();
            switch (respuesta){
                case 1 -> verOrdenes();
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }
    public void menuVerOrdenesFlujo(){
        int respuesta;
        do {
            respuesta = menuVerOrdenes();
            switch (respuesta){
                case 0 -> {}
                case 1 -> verOrdenes();
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }

    private void verPedidos(){
        id = getIdUsuario("revisar", true);
        configuraUI(10);
    }
    private void verOrdenes(){
        MySQLHelper mySQLHelper = new MySQLHelper();
        id_pedido = getIdPedido("revisar", true);
        fecha = mySQLHelper.obtenerFechaPorIdPedido(id_pedido);
        List<String> lista = mySQLHelper.obtenerIdPorFecha(fecha);
        id = Integer.parseInt(lista.get(1));

        configuraUI(11);
    }

    public int getIdUsuario(String valor, boolean aDeBaseDatos) {
        boolean aux = false;
        int id = -1;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese el ID del usuario a " + valor + " o ingrese 0 para cancelar.");

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
    public int getIdPedido(String valor, boolean aDeBaseDatos) {
        boolean aux = false;
        int id = -1;
        do{
            System.out.println("----------------------------------------------------");
            System.out.println("Ingrese el ID del pedido a " + valor + " o ingrese 0 para cancelar.");

            String str;
            int i;
            i = 0;
            Scanner scanner = new Scanner(System.in);
            str = scanner.next();
            while(i < str.length() && Character.isDigit(str.charAt(i))) i++;
            if (i < str.length()) {
                str ="-1";
                System.out.println("----------------------------------------------------");
                System.out.println("-------->El valor ingresado es incorrecto! <---------");
            }
            else {
                id = Integer.parseInt(str);
                if(verificaIDPedido(id, aDeBaseDatos)) aux = true;
                if (!aux) {
                    System.out.println("----------------------------------------------------");
                    System.out.println("-------->El valor ingresado es incorrecto <---------");
                }
            }
        }while (!aux && id != 0 );

        return id;
    }
    // ----------------------------------------------------------------
    // Métodos de opciones
    // ----------------------------------------------------------------
    public int menuPrincipal() {
        tituloMenu("Menu Principal (Administrador)");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Ver listado de productos");
        System.out.println("[2] Modificar datos de los productos");
        System.out.println("[3] Ver todos los usuarios que realizaron una compra");
        System.out.println("[4] Ver listado de productos seleccionados por el usuario");
        System.out.println("");
        System.out.println("[0] Cerrar Sesion");
        System.out.println("----------------------------------------------------");
        return scannerInt();
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
        return scannerInt();
    }
    public int menuModificarProducto(){
        tituloMenu("Menu Ver Productos (Administrador)");
        cargaLista(LISTA_PRODUCTOS);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Agregar Cantidad.");
        System.out.println("[2] Quitar Cantidad.");
        System.out.println("[3] Modificar Nombre.");
        System.out.println("[4] Modificar Detalle.");
        System.out.println("[5] Modificar Precio.");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scannerInt();
    }
    public int menuVerUsuarios(){
        tituloMenu("Menu Ver Usuarios con Compras (Administrador)");
        cargaLista(LISTA_USUARIOS);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Ver Pedidos");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scannerInt();
    }
    public int menuVerPedidos(){
        tituloMenu("Menu Ver Pedidos de Usuario (Administrador)");
        cargaLista(LISTA_PEDIDOS);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Ver ordenes");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scannerInt();
    }
    public int menuVerOrdenes(){
        tituloMenu("Menu Ver Ordenes de Pedido (Administrador)");
        cargaLista(LISTA_ORDENES);
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("");
        System.out.println("[0] Volver");
        System.out.println("----------------------------------------------------");
        return scannerInt();
    }

    // ----------------------------------------------------------------
    // Métodos generales
    // ----------------------------------------------------------------
    private int scannerInt(){
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
    private boolean verificaCantidad(int id, int cantidad, boolean operacion) {
        if (application.getUsuario().getType() == 0){
            if (operacion) return actionHandler.consultaStock(id, cantidad);
            else return ((Cliente)(application.getUsuario())).getCarrito().verificaCantidad(id,cantidad);
        } else {
            return actionHandler.consultaStock(id, cantidad);
        }
    }
    private boolean verificaID(int id, boolean aDeBaseDatos) {
       if (application.getUsuario().getType() == 0) {
           if (aDeBaseDatos) return actionHandler.consultaSiExiste(id,"products_db");
           else return ((Cliente) application.getUsuario()).getCarrito().consultaSiExisteID(id);
       } else {
           return actionHandler.consultaSiExiste(id, "products_db");
       }
    }
    private boolean verificaIDPedido(int id, boolean aDeBaseDatos) {
        if (application.getUsuario().getType() == 0) {
            if (aDeBaseDatos) return actionHandler.consultaSiExiste(id,"products_db");
            else return ((Cliente) application.getUsuario()).getCarrito().consultaSiExisteID(id);
        } else {
            return actionHandler.consultaSiExiste(id,"shopping_cart_db");
        }
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
            if (lista.size()>0){
                int aux = 0;
                for (int i = 0; i < mayorPeso.length; i++) aux += mayorPeso[i]+3;
                float cnt = 0;
                for (List<String> cadena : lista) cnt += Float.parseFloat(cadena.get(cadena.size()-1));
                aux -= (3 + 7 + String.valueOf(cnt).length());

                Arrays.fill(separador, "");
                for (int i = 0; i < aux; i++) separador[0] += " ";

                System.out.println(separador[0] + "Total: "
                        + cnt);
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
                        actionHandler.obtenerProductos(false),false);
                System.out.println("----------------------------------------------------");
            }
            case 1 -> {
                System.out.println("Lista de usuarios con compras");
                List<List<String>> listList = actionHandler.obtenerUsuarios(true);
                for (List<String> lista : listList) lista.add("true");
                tablaFix(new String[]{"ID","Usuario","Pedidos"},
                        listList,
                        false);
                System.out.println("----------------------------------------------------");
            }
            case 2 -> {
                System.out.println("Lista de Pedidos de Usuario");
                MySQLHelper mySQLHelper = new MySQLHelper();
                List<List<String>> listList = mySQLHelper.obtenerPedidos(id);
                tablaFix(new String[]{"ID Pedido", "ID Usuario", "Fecha"},
                        listList,
                        false);
                System.out.println("----------------------------------------------------");
            }
            case 3 -> {
                System.out.println("Lista de Ordenes del Pedido");
                MySQLHelper mySQLHelper = new MySQLHelper();
                List<List<String>> listList = mySQLHelper.obtenerOrdenes(id_pedido, fecha);
                //"product_id","amount","price"
                for(int i = 0; i < listList.size(); i++) listList.get(i).addAll(1,
                        mySQLHelper.obtenerDatosEtiquetas(
                                Integer.parseInt(listList.get(i).get(0)),
                                Arrays.asList("nombre","descripcion"),
                                "products_db")
                );
                for (List<String> lista : listList) lista.add(
                        String.valueOf(Integer.parseInt(lista.get(3))*Float.parseFloat(lista.get(4))
                ));
                tablaFix(new String[]{"ID","Producto","Descripcion","Cantidad","Precio", "SubTotal"},
                        listList,
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
            case 6 -> menuPrincipalFlujo();
            case 7 -> menuVerProductoFlujo();
            case 8 -> menuModificalProductoFlujo();
            case 9 -> menuVerUsuariosFlujo();
            case 10 -> menuVerPedidosFlujo();
            case 11 -> menuVerOrdenesFlujo();
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
            case 3 -> {
                System.out.println("ERROR: EL producto ya existe.");
                System.out.println("----------------------------------------------------");
            }
            case 4 -> {
                System.out.println("ERROR: No se pudo realizar la carga.");
                System.out.println("----------------------------------------------------");
            }
            default -> {}
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
        String descripcion;
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese la descripcion del producto.");

        Scanner scanner = new Scanner(System.in);
        descripcion = scanner.nextLine();
        return descripcion;
    }
    @Override
    public float getPrecio() {
        float precio;
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese el precio del producto.");

        Scanner scanner = new Scanner(System.in);
        precio = scanner.nextFloat();
        return precio;
    }
    @Override
    public int getStock() {
        int stock;
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese el stock del producto.");

        Scanner scanner = new Scanner(System.in);
        stock = scanner.nextInt();
        return stock;
    }
    @Override
    public void endUp(int valor) {

    }
}
