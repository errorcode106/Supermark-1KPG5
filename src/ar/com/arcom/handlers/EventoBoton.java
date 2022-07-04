package ar.com.arcom.handlers;

import ar.com.arcom.Application;
import ar.com.arcom.ui.AdministradorUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventoBoton implements ActionListener {
    private Application application;
    private ActionHandler actionHandler;
    private Login login;
    private AdministradorUI frame;

    public EventoBoton(Login login, Application application) {
        this.login = login;
        this.application = application;
        actionHandler = new ActionHandler(application);
    }
    public EventoBoton(AdministradorUI frame, Application application) {
        this.frame = frame;
        this.application = application;
        actionHandler = new ActionHandler(application);
    }

    public void actionPerformed(ActionEvent event){
        String cmd = event.getActionCommand();
        switch (cmd){
            case "cmd_exit" -> actionHandler.exit();
            case "cmd_login" -> {
                int valor = actionHandler.iniciarSecision(login);
                if (valor == -1) JOptionPane.showMessageDialog(null, "Usuario no existe");
                else if(valor == 0) JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                else login.endUp(valor);
            }
            case "cmd_to_shopping_cart" -> {
                frame.configuraUI(frame.CLIENTE_VER_CARRITOS);
            }
            case "cmd_inicio_cliente" -> {
                frame.configuraUI(frame.CLIENTE_UI);
            }
            case "cmd_logoff" -> {
                frame.loginScreen();
                actionHandler.cerrarSesion();
            }
            case "cmd_sing" -> {
                int valor = actionHandler.registrarse(login);
                if (valor == -1) JOptionPane.showMessageDialog(null, "Usuario ya existe");
                else if (valor == 0) JOptionPane.showMessageDialog(null, "Error al crear el usuario, intente de nuevo");
                else login.endUp(valor);
            }
            default -> {}
        }
    }
}
