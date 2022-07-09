package ar.com.arcom;

import ar.com.arcom.bin.Usuario;
import ar.com.arcom.console.InterfaceConsole;
import ar.com.arcom.mysql.DataBaseInfo;
import ar.com.arcom.ui.UI;

import javax.swing.*;

public class Application {
    public final String TITULO = "SuperMark v0.20220709";
    private Usuario usuario;
    private InterfaceConsole consoleInterface;
    private UI ui;

    public void play(){
        JOptionPane.showMessageDialog(null,"PROYECTO REALIZADO POR:\n"+ "Martínez Cruz, Flavio G.\n" +
                "Martinez, DanteAlberto.\n" +
                "Karanicolas,Tatiana Gisel.\n" +
                "Ortega, Janet Judith Guadalupe.\n" +
                "Vidaurre, José Luis.\n");

        int seleccion = JOptionPane.showOptionDialog( null,"¿Qué versión de SuperMark desea?",
                TITULO,JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,
                new Object[] { "Consola ", "GUI"},"SuperMark GUI");
        switch (seleccion){
            case 0 -> {
                consoleInterface = new InterfaceConsole(this);

                int seleccion2 = JOptionPane.showOptionDialog( null,"¿Cual Base de Datos desea?",
                        TITULO,JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,null,
                        new Object[] { "Nube", "Local"},"Nube");
                if (seleccion2 == 1) DataBaseInfo.setCustomDataBaseInfo();
                else DataBaseInfo.setDefaultDataBaseInfo();

                consoleInterface.play();
            }
            case 1 -> {
                int seleccion2 = JOptionPane.showOptionDialog( null,"¿Cual Base de Datos desea?",
                        TITULO,JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,null,
                        new Object[] { "Nube", "Local"},"Nube");
                if (seleccion2 == 1) DataBaseInfo.setCustomDataBaseInfo();
                else DataBaseInfo.setDefaultDataBaseInfo();
                ui = new UI(this);
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
}
