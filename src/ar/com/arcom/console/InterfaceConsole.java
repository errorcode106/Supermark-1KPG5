package ar.com.arcom.console;

import ar.com.arcom.Application;
import ar.com.arcom.handlers.ActionHandler;
import ar.com.arcom.handlers.Login;

import java.util.Scanner;

public class InterfaceConsole implements Login {
    // Atributos
    private Application application;
    private ActionHandler actionHandler;
    private Scanner scanner;

    // Constructor
    public InterfaceConsole(Application application) {
        this.application = application;
        scanner = new Scanner(System.in);
        actionHandler = new ActionHandler(application);
    }

    // ----------------------------------------------------------------
    // Métodos de flujo
    // ----------------------------------------------------------------
    public void play(){
        int respuesta;
        do {
            respuesta = login();
            switch (respuesta){
                case 0 -> actionHandler.exit();
                case 1 -> actionHandler.iniciarSecision(this);
                case 2 -> actionHandler.registrarse(this);
                default -> {System.out.println("ERROR: Ingrese un [valor] valido.");}
            }
        }while (respuesta != 0);
    }

    // ----------------------------------------------------------------
    // Menu Login
    // ----------------------------------------------------------------
    private int login(){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO);
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Iniciar sesion");
        System.out.println("[2] Registrarse");
        System.out.println("");
        System.out.println("[0] Salir");
        System.out.println("----------------------------------------------------");
        return scanner();
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
    // Implementacion Interfaz Login
    // ----------------------------------------------------------------
    @Override
    public String getUser() {
        System.out.println("----------------------------------------------------");
        System.out.print("Ingrese usuario: ");
        return scanner.next();
    }
    @Override
    public String getPassword() {
        System.out.println("----------------------------------------------------");
        System.out.print("Ingrese contrasena: ");
        return scanner.next();
    }
    @Override
    public boolean stayLoggedIn() {
        return false;
    }
    @Override
    public void endUp(int valor) {
        switch (valor){
            case -1 -> {
                System.out.println("----------------------------------------------------");
                System.out.println("--------> El usuario ingresado no existe. <---------");
            }
            case 0 -> {
                System.out.println("----------------------------------------------------");
                System.out.println("---------> La constrasena es incorrecta. <----------");
            }
            case 1 -> {
                actionHandler.configuraUI(new ClientConsole(application,actionHandler),1);
            }
            case 2 -> {
                AdminConsole adminConsole = new AdminConsole(application,actionHandler);
                adminConsole.menuPrincipalFlujo();
            }
            case 3 -> {
                System.out.println("----------------------------------------------------");
                System.out.println("--------> El usuario ingresado ya existe. <---------");
            }
            case 4 -> {
                System.out.println("--------------------------------------------------------------");
                System.out.println("-> Algo sucedio, reinicia el programa e intenta nuevamente. <-");
            }

            default -> {}
        }
    }
}
