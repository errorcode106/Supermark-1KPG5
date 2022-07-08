package ar.com.arcom.ui;

import ar.com.arcom.Application;
import ar.com.arcom.handlers.ActionHandler;
import ar.com.arcom.handlers.Login;
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

    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    public void actionPerformed(ActionEvent event){
        String cmd = event.getActionCommand();
        switch (cmd){
            case "cmd_exit" -> actionHandler.exit();
            case "cmd_login" -> actionHandler.iniciarSecision(login);
            case "cmd_sing" ->  actionHandler.registrarse(login);
            case "cmd_logoff" -> actionHandler.cerrarSesion(frame);
            case "cmd_to_shopping_cart" -> actionHandler.configuraUI(frame,frame.CLIENTE_VER_CARRITOS);
            case "cmd_inicio_cliente" -> actionHandler.configuraUI(frame,frame.CLIENTE_UI);
            case "cmd_products" -> actionHandler.configuraUI(frame,frame.CLIENTE_VER_PRODUCTOS);
            case "cmd_add" -> actionHandler.agregaAlCarrito(frame);
            case "cmd_edit" -> actionHandler.modificarProducto(frame, false);
            default -> {}
        }
    }
}
