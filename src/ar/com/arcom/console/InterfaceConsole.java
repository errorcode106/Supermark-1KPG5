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
    public void play(){
        int respuesta;
        do {
            respuesta = login();
            switch (respuesta){
                case 0 -> actionHandler.exit();
                case 1 -> {
                    int valor = actionHandler.iniciarSecision(this);
                    if (valor == -1) usuarioNoExiste();
                    else if(valor == 0) contraseniaIncorrecta();
                    else {
                        if (valor == 1) {
                            ClientConsole clientConsole = new ClientConsole(application,actionHandler);
                            clientConsole.menuCliente();
                        } else {
                            AdminConsole adminConsole = new AdminConsole(application,actionHandler);
                            adminConsole.menuAdministrador();
                        }
                    }
                }
                case 2 -> {
                    int valor = actionHandler.registrarse(this);
                    if (valor == -1) usuarioYaExiste();
                    else if(valor == 0) errorAlRegistrarse();
                    else {
                        ClientConsole clientConsole = new ClientConsole(application,actionHandler);
                        clientConsole.menuCliente();
                    }
                }
                default -> {}
            }
        }while (respuesta != 0);
    }
    private int scanner(){
        System.out.print("Respuesta: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    // ----------------------------------------------------------------
    // Menu Login
    private int login(){
        supermarkVersionNombre();
        System.out.println("----------------------------------------------------");
        System.out.println("Ingrese [valor] correspondiente a la opcion elegida.");
        System.out.println("[1] Iniciar sesion");
        System.out.println("[2] Registrarse");
        System.out.println("");
        System.out.println("[0] Salir");
        System.out.println("----------------------------------------------------");
        return scanner();
    }
    public void usuarioNoExiste() {
        System.out.println("----------------------------------------------------");
        System.out.println("--------> El usuario ingresado no existe. <---------");
    }
    public void usuarioYaExiste() {
        System.out.println("----------------------------------------------------");
        System.out.println("--------> El usuario ingresado ya existe. <---------");
    }
    public void contraseniaIncorrecta() {
        System.out.println("----------------------------------------------------");
        System.out.println("---------> La constrasena es incorrecta. <----------");
    }
    public void errorAlRegistrarse() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("-> Algo sucedio, reinicia el programa e intenta nuevamente. <-");
    }
    private void supermarkVersionNombre(){
        System.out.println("----------------------------------------------------");
        System.out.println(application.TITULO);
    }
    // ----------------------------------------------------------------
    // Implementacion Interfaz Login
    @Override
    public String getUser() {
        System.out.println("----------------------------------------------------");
        System.out.print("ingrese usuario: ");
        return scanner.next();
    }
    @Override
    public String getPassword() {
        System.out.println("----------------------------------------------------");
        System.out.print("ingrese contrasena: ");
        return scanner.next();
    }
    @Override
    public boolean stayLoggedIn() {
        return false;
    }
    @Override
    public void endUp(int valor) {
    }
}
