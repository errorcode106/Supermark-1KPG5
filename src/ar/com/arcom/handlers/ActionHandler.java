package ar.com.arcom.handlers;

import ar.com.arcom.Application;
import ar.com.arcom.bin.*;
import ar.com.arcom.mysql.MySQLHelper;
import ar.com.arcom.ui.UI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ActionHandler {
    // ----------------------------------------------------------------
    // Atributos
    // ----------------------------------------------------------------
    private MySQLHelper mySQLHelper;
    private Application application;
    private boolean isLogin;
    private String user;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public ActionHandler(Application application) {
        this.application = application;
        this.mySQLHelper = new MySQLHelper();
        isLogin = false;
    }

    // ----------------------------------------------------------------
    // Métodos de Login
    // ----------------------------------------------------------------
    /**
     *
     * @param login
     * @return 1: si coneccion fue exitosa y ademas el usuario es un cliente y 2 si es un administrador. 0: si la contraseña es incorrecta. -1: si el usuario no existe en la base de datos.
     */
    public void iniciarSecision(Login login){
        int valor;
        user = login.getUser();
        if(mySQLHelper.existeElUsuario(user)){
            if (mySQLHelper.compararUsuarioContrasenia(user, login.getPassword())){
                isLogin = true;
                if (mySQLHelper.getType(user) == 0) {
                    application.setUsuario(new Cliente(user));
                    application.getUsuario().setType(0);
                    valor =  1;
                } else {
                    application.setUsuario(new Administrador(user));
                    application.getUsuario().setType(1);
                    valor =  2;
                }
            } else valor =  0;
        } else valor = -1;

        login.endUp(valor);
    }
    /**
     *
     * @param login
     * @return 3: si el usuario ya existe en la base de datos. 4: si ocurrio algun error, 1: si fue correcto.
     *
     */
    public void registrarse(Login login){
        int valor;
        user = login.getUser();
        if(!mySQLHelper.existeElUsuario(user)){
            int aux = mySQLHelper.agregaUsuario(user, login.getPassword());
            if(aux == 1){
                isLogin = true;
                application.setUsuario(new Cliente(user));
                valor = 1;
            } else valor =  4;
        } else valor =  3;
        login.endUp(valor);
    }
    public void cerrarSesion(EndUp endUp){
        System.out.println(application.getUsuario().getType());
        if (application.getUsuario().getType() == 0)
            for (Articulo articulo : ((Cliente)application.getUsuario()).getCarrito().getArticulos())
                mySQLHelper.agregarStock(articulo.getId(),articulo.getCantidad());
        application.setUsuario(null);
        endUp.endUp(0);
        System.gc();
    }

    // ----------------------------------------------------------------
    // Métodos de Cliente
    // ----------------------------------------------------------------
    public void agregaAlCarrito(UIHelper uiHelper) {
        int id = uiHelper.getID("agregar",true);

        if(id != 0 && mySQLHelper.consultaStock(id,1)){
            Integer cantidad = uiHelper.getCantidad(id, "agregar");
            if (cantidad != null && cantidad != 0) {
                if(mySQLHelper.consultaStock(id, cantidad)){
                    if (((Cliente)(application.getUsuario())).getCarrito().consultaSiExisteID(id)){
                        mySQLHelper.quitaStock(id, cantidad);
                        ((Cliente)(application.getUsuario())).getCarrito().agregaCantidad(id, cantidad);
                        ((Cliente)(application.getUsuario())).getCarrito().recalcularTotales();
                    } else ((Cliente)(application.getUsuario())).agregar(
                            mySQLHelper.crearArticulo(id,cantidad)
                    );
                } else uiHelper.error(0);
            }
        } uiHelper.error(1);
    }
    public void agregarCantidadProducto(UIHelper uiHelper, boolean aBaseDeDatos){
        int id = uiHelper.getID("agregar", false);

        if (aBaseDeDatos){
            int cantidad = uiHelper.getCantidad(id, "agregar");
            mySQLHelper.agregarStock(id, cantidad);
        } else {
            if(id != 0){
                int cantidad = uiHelper.getCantidad(id, "agregar");
                if (cantidad <= 0) {
                    if(mySQLHelper.consultaStock(id, cantidad)){
                        mySQLHelper.quitaStock(id, cantidad);
                        ((Cliente)(application.getUsuario())).getCarrito().agregaCantidad(id, cantidad);
                        ((Cliente)(application.getUsuario())).getCarrito().recalcularTotales();
                    } else uiHelper.error(0);
                }
            }
        }
    }
    public void quitarCantidadProducto(UIHelper uiHelper) {
        int id = uiHelper.getID("quitar", false);
        if (application.getUsuario().getType() == 0){
            if(id != 0){
                int cantidad = uiHelper.getCantidad(id, "quitar");
                if (cantidad != 0) {
                    if(((Cliente)(application.getUsuario())).getCarrito().verificaCantidad(id, cantidad)){
                        mySQLHelper.agregarStock(id, cantidad);
                        ((Cliente)(application.getUsuario())).getCarrito().quitarCantidad(id, cantidad);
                        ((Cliente)(application.getUsuario())).getCarrito().recalcularTotales();
                    } else uiHelper.error(0);
                }
            }
        } else if(id != 0){
            int cantidad = uiHelper.getCantidad(id, "quitar");
            if (cantidad != 0) {
                if(mySQLHelper.consultaStock(id, cantidad)){
                    mySQLHelper.quitaStock(id, cantidad);
                }
            }
        }
    }

    public void comprar(UIHelper uiHelper) {
        List<Articulo> lista = ((Cliente)application.getUsuario()).getCarrito().getArticulos();
        if (lista.size()>0){
            if(!mySQLHelper.comprarListaArticulos(application.getUsuario().getNombre(),lista)) uiHelper.error(2);
            ((Cliente)(application.getUsuario())).getCarrito().setArticulos(new ArrayList<>());
            System.gc();
            uiHelper.configuraUI(3);
            JOptionPane.showMessageDialog(null, "La compra se a realizado con exito");
        }
    }
    public void vaciarCarrito() {
        for(Articulo articulo : ((Cliente)(application.getUsuario())).getCarrito().getArticulos())
            mySQLHelper.agregarStock(articulo.getId(),articulo.getCantidad());
        ((Cliente)(application.getUsuario())).getCarrito().setArticulos(null);
        System.gc();
    }

    public List<List<String>> obtenerProductos(boolean con_existencias){
        return mySQLHelper.obtenerProductos(con_existencias);
    }
    public List<List<String>> obtenerArticulos(List<Articulo> lista) {
        return (lista != null) ? mySQLHelper.obtenerArticulos(lista) : new ArrayList<List<String>>();
    }
    public boolean consultaStock(int id, int cantidad) {
        return mySQLHelper.consultaStock(id, cantidad);
    }
    public boolean consultaSiExiste(int id, String baseDatos ){
        return mySQLHelper.consultaSiExiste(id, baseDatos);
    }

    // ----------------------------------------------------------------
    // Métodos de Administrador
    // ----------------------------------------------------------------
    public void modificarProducto(UIHelper uiHelper, boolean aDeBaseDatos) {
        if (aDeBaseDatos){
            int valor = uiHelper.getID("modificar",true);
            if (valor != 0) uiHelper.configuraUI(3);
        } else {
            int valor = uiHelper.getID("modificar",false);
            if (valor != 0) uiHelper.configuraUI(4);
        }
    }
    public boolean consultaSiExisteProducto(String nombre) {
        return mySQLHelper.consultaSiExisteNombre(nombre);
    }
    public void cargarProducto(UIHelper uiHelper) {
        String nombre = uiHelper.getNombre();
        if (nombre != null && !consultaSiExisteProducto(nombre)) {
            String descripcion = uiHelper.getDescripcion();
            float price = uiHelper.getPrecio();
            int stock = uiHelper.getStock();
            if(price > 0 && stock > 0){
                mySQLHelper.cargarProducto(new Producto(0,nombre, descripcion, price, stock));
            } else uiHelper.error(4);
        } else uiHelper.error(3);
        uiHelper.configuraUI(7);
    }
    public List<List<String>> obtenerUsuarios(boolean soloConPedidos) {
        return mySQLHelper.obtenerUsuarios(soloConPedidos);
    }
    // ----------------------------------------------------------------
    // Métodos generales
    // ----------------------------------------------------------------
    public void exit() {
        System.exit(0);
    }
    public void configuraUI(UIHelper uiHelper, int valor) {
        uiHelper.configuraUI(valor);
    }
    public int buscarProductoNombre(UIHelper uiHelper) {
        String nombre = uiHelper.getNombre();
        return mySQLHelper.buscaPorNombre(nombre);
    }

    public void mermar(EndUp endUp) {
        endUp.endUp(3);
    }
    public void aumentar(EndUp endUp) {
        endUp.endUp(2);
    }
    public void acept(EndUp endUp) {
        endUp.endUp(1);

    }
    public void dispose(EndUp endUp) {
        endUp.endUp(0);
    }

    public void modificaNombre(UIHelper uiHelper) {
        int id = uiHelper.getID("modificar",true);
        mySQLHelper.modificaNombre(id,uiHelper.getNombre());
    }
    public void modificaDescripcion(UIHelper uiHelper) {
        int id = uiHelper.getID("modificar",true);
        mySQLHelper.modificaDescripcion(id,uiHelper.getDescripcion());
    }
    public void modificaPrecio(UIHelper uiHelper) {
        int id = uiHelper.getID("modificar",true);
        mySQLHelper.modificaPrecio(id,String.valueOf(uiHelper.getPrecio()));
    }
    public void modificarProductoFinal(EndUp endUp) {
        endUp.endUp(1);
    }
    public void verPedidos(UIHelper uiHelper) {
        uiHelper.configuraUI(10);
    }
    public void verOrdenes(UIHelper uiHelper) {
        uiHelper.configuraUI(11);
    }
}
