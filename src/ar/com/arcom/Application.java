package ar.com.arcom;

import ar.com.arcom.bin.Usuario;
import ar.com.arcom.console.ConsoleInterface;
import ar.com.arcom.ui.AdministradorUI;

import javax.swing.*;

public class Application {
    public final String TITULO = "SuperMark v0.20220630";
    private Usuario usuario;
    private ConsoleInterface consoleInterface;

    public Application(){

    }

    public void play(){
        int seleccion = JOptionPane.showOptionDialog( null,"¿Qué versión de SuperMark desea?",
                TITULO,JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,
                new Object[] { "Consola", "GUI"},"SuperMark GUI");
        switch (seleccion){
            case 0 -> {
                consoleInterface = new ConsoleInterface(this);
                consoleInterface.play();
            }
            case 1 -> {
                AdministradorUI ui = new AdministradorUI(this);
                ui.setVisible(true);
            }
            default -> {
                System.exit(0);
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static void main(String[] arg){
        Application application = new Application();
        application.play();
    }
    // flavio raton
}
