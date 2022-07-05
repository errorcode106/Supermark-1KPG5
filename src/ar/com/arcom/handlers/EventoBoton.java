package ar.com.arcom.handlers;

import ar.com.arcom.Application;
import ar.com.arcom.ui.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventoBoton implements ActionListener {
    private Application application;
    private ActionHandler actionHandler;
    private Login login;
    private UI frame;

    public EventoBoton(Login login, Application application) {
        this.login = login;
        this.application = application;
        actionHandler = new ActionHandler(application);
    }
    public EventoBoton(UI frame, Application application) {
        this.frame = frame;
        this.application = application;
        actionHandler = new ActionHandler(application);
    }

    public void actionPerformed(ActionEvent event){
        String cmd = event.getActionCommand();
        switch (cmd){
            case "cmd_exit" -> actionHandler.exit();
            case "cmd_login" -> actionHandler.iniciarSecision(login);
            case "cmd_sing" ->  actionHandler.registrarse(login);
            case "cmd_logoff" -> actionHandler.cerrarSesion();
            case "cmd_to_shopping_cart" -> frame.configuraUI(frame.CLIENTE_VER_CARRITOS);
            case "cmd_inicio_cliente" -> frame.configuraUI(frame.CLIENTE_UI);

            default -> {}
        }
    }
}
