package ar.com.arcom.handlers;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Administrador;
import ar.com.arcom.bin.Articulo;
import ar.com.arcom.bin.Cliente;
import ar.com.arcom.mysql.MySQLHelper;

import java.util.List;

public class ActionHandler {
    private MySQLHelper mySQLHelper;
    private Application application;
    private boolean isLogin;
    private String user;

    public ActionHandler(Application application) {
        this.application = application;
        this.mySQLHelper = new MySQLHelper();
        isLogin = false;
    }

    /**
     *
     * @param login
     * @return 1: si coneccion fue exitosa y ademas el usuario es un cliente y 2 si es un administrador. 0: si la contraseña es incorrecta. -1: si el usuario no existe en la base de datos.
     */
    public int iniciarSecision(Login login){
        user = login.getUser();
        if(mySQLHelper.existeElUsuario(user)){
            if (mySQLHelper.compararUsuarioContrasenia(user, login.getPassword())){
                isLogin = true;
                if (mySQLHelper.getType(user) == 0) {
                    application.setUsuario(new Cliente(user));
                    return 1;
                } else {
                    application.setUsuario(new Administrador(user));
                    return 2;
                }
            } else return 0;
        } else return -1;
    }
    /**
     *
     * @param login
     * @return -1: si el usuario ya existe en la base de datos. 0: si ocurrio algun error, 1: si fue todo correcto.
     *
     */
    public int registrarse(Login login){
        user = login.getUser();
        if(!mySQLHelper.existeElUsuario(user)){
            int aux = mySQLHelper.añadeUsuario(user, login.getPassword());
            if(aux != -1){
                isLogin = true;
                application.setUsuario(new Cliente(user));
                return 1;
            } return 0;
        } else return -1;
    }
    public void exit() {
        System.exit(0);
    }
    public void cerrarSesion(){
        application.setUsuario(null);
        System.gc();
    }
    public List<List<String>> obtenerProductos(){
        return mySQLHelper.obtenerProductos();
    }

    public String getUser() {
        return user;
    }

    public void agregaCarrito(int id, int cantidad) {
        if(mySQLHelper.consultaStock(id, cantidad)) mySQLHelper.quitaStock(id, cantidad);
    }

    public List<List<String>> obtenerArticulos(List<Articulo> lista) {
        return mySQLHelper.obtenerArticulos(lista);
    }

    public boolean modificarArticuloCarrito(int id, int cantidad) {
        if(mySQLHelper.consultaStock(id, cantidad)) {
            mySQLHelper.quitaStock(id, cantidad);
            return true;
        } else return false;
    }

    public void modificarQuitarArticuloCarrito(int id, int cantidad) {
        mySQLHelper.agregarStock(id, cantidad);
    }

    public boolean consultaStock(int id, int cantidad) {
        return mySQLHelper.consultaStock(id, cantidad);
    }
}
