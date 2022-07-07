package ar.com.arcom.mysql;

import ar.com.arcom.bin.Articulo;
import ar.com.arcom.bin.Producto;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Pasos para usar JDBC:
1. Crear una instancia del JDBC Driver.
2. Especificar la url y credenciales de la BDD.
3. Establecer una conexión usando el driver que crea el objeto Connection.
4. Crear un objeto Statement, usando Connection.
5. Armar el postulado SQL y enviarlo a ejecución usando el Statement.
6. Recibir los resultados en el objeto ResultSet.
*/


public class MySQLHelper {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private DataBaseInfo dataBaseInfo;
    private boolean openConnection;

    public MySQLHelper() {
        connection = null;
        statement = null;
        resultSet = null;

        openConnection = false;
        dataBaseInfo = new DataBaseInfo();

    }
    private void openConection(){
        //PASO 1: Abrir una Conexion
        try {
            connection = DriverManager.getConnection(
                    dataBaseInfo.getDB_URL(),
                    dataBaseInfo.getUSER(),
                    dataBaseInfo.getPASSWORD()
            );

            openConnection = true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
            openConnection = false;
            clean();
        }
    }
    private void clean(){
        //PASO4: Entorno de Limpieza
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            // Bloque finalmente utilizado para cerrar recursos
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException se2) {
                se2.printStackTrace();
                JOptionPane.showMessageDialog(null, se2.getMessage());
            }// Nada que podamos hacer
            try{
                if(connection!=null) connection.close();
            }catch(SQLException se){
                se.printStackTrace();
                JOptionPane.showMessageDialog(null, se.getMessage());
            }
        }
    }

    public boolean existeElUsuario(String user){
        return SimpleQueryCompare("user", user);
    }
    public boolean compararUsuarioContrasenia(String user, String password) {
        return runQueryCompare("user", "password", user, password);
    }
    public int getType(String user) {
        return Integer.parseInt(simpleQuery("user", user, "int"));
    }
    public int agregaUsuario(String user, String password) {
        int aux = 4;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "INSERT INTO bsi5brxpk0wz9ygdti6z.users_db (id,user,password,type) VALUES(0,'" + user + "', '" + password + "',0)";
                statement.executeUpdate(sql);
                aux = 1;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();

        return aux;
    }
    private String simpleQuery(String label, String dataLabel, String dataType){
        String aux = "";
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.users_db WHERE " + label + " = " + "'" + dataLabel + "'";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) {
                    aux = extractDataProductos("type",dataType);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }
    private boolean SimpleQueryCompare(String label, String dataLabel){
        boolean aux = false;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.users_db WHERE " + label + " = " + "'" + dataLabel + "'";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) aux = true;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }
    private boolean runQueryCompare(String labelA, String labelB, String dataLabelA, String dataLabelB) {
        String aux = "";
        openConection();

        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.users_db WHERE " + labelA + " = " + "'" + dataLabelA + "'";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) {
                    try {
                        aux = resultSet.getString("password");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux.equals(dataLabelB);
    }
    private String extractDataProductos(String label, String dataType){
        //PASO 3: Extraer datos de un ResulSet
        String valor = "";
        try {
            //Recibir por tipo de columna
            valor = resultSet.getString(label);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        System.out.println(valor);
        return valor;
    }
    public List<List<String>> obtenerProductos(boolean con_existencias) {
        return dameProducto(con_existencias);
    }
    private List<List<String>> dameProducto(boolean con_existencias){
        List<List<String>> aux = new ArrayList<>();
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) {
                    List<String> labels = Arrays.asList("id","nombre","descripcion","precio","stock");
                    aux = extractDataProductos(con_existencias, labels);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }
    private List<List<String>> extractDataProductos(boolean con_existencias, List<String> labels) {
        //PASO 3: Extraer datos de un ResulSet
        List<List<String>> valor = new ArrayList<>();
        try {
            do{
                List<String> aux = new ArrayList<>();
                if (resultSet.getInt("stock") > 0 || !con_existencias) {
                    for (String label : labels) aux.add(resultSet.getString(label));
                    valor.add(aux);
                }
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return valor;
    }
    public void quitaStock(int id, int cantidad) {
        int stock = 0;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db WHERE " + "id" + " = " + "'" + id + "'";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) stock = resultSet.getInt("stock");
                sql = "UPDATE bsi5brxpk0wz9ygdti6z.products_db SET `stock` = '" + (stock-cantidad) + "' WHERE (`id` = '" + id +"')";
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
    }
    public List<List<String>> obtenerArticulos(List<Articulo> lista) {
        List<List<String>> articulos = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            articulos.add(dameArticulo(lista.get(i).getId(),
                    Arrays.asList("id","nombre","descripcion","precio")));

            articulos.get(articulos.size()-1).add(String.valueOf(lista.get(i).getCantidad()));

            articulos.get(articulos.size()-1).add(
                    String.valueOf(lista.get(i).getCantidad() * Float.parseFloat(
                            articulos.get(articulos.size()-1).get(articulos.get(articulos.size()-1).size()-2)
                    )));
        }
        return articulos;
    }
    private List<String> dameArticulo(int id, List<String> labels) {
        List<String> aux = new ArrayList<>();
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db WHERE id = " + "'" + id + "'";
                resultSet = statement.executeQuery(sql);
                if (resultSet.next()) for (String label : labels) aux.add(resultSet.getString(label));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }
    public void agregarStock(int id, int cantidad) {
        int stock = 0;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db WHERE " + "id" + " = " + "'" + id + "'";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) stock = resultSet.getInt("stock");
                sql = "UPDATE bsi5brxpk0wz9ygdti6z.products_db SET `stock` = '" + (stock+cantidad) + "' WHERE (`id` = '" + id +"')";
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
    }


    public boolean consultaStock(int id, int cantidad) {
        boolean aux = false;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db WHERE " + "id" + " = " + "'" + id + "'";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) {
                    aux = cantidad <= resultSet.getInt("stock");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }
    public boolean consultaSiExiste(int id) {
        boolean aux = false;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db WHERE " + "id" + " = " + "'" + id + "'";
                resultSet = statement.executeQuery(sql);
                aux = resultSet.next();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }

    public Articulo crearArticulo(int id, int cantidad) {
        quitaStock(id, cantidad);
        Articulo articulo = null;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db WHERE " + "id" + " = " + "'" + id + "'";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) {
                    articulo = new Articulo();
                    List<String> labels = Arrays.asList("id","nombre","descripcion","precio");
                    for (String label : labels) articulo.set(label,resultSet.getString(label));
                    articulo.set("cantidad",String.valueOf(cantidad));
                    articulo.calculaTotal();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return articulo;
    }

    public boolean cargarProducto(Producto producto) {
        boolean aux = false;
        if (!consultaSiExisteNombre(producto.getNombre())){
            openConection();
            if (openConnection){
                try {
                    statement = connection.createStatement();
                    String sql;
                    sql = "INSERT INTO bsi5brxpk0wz9ygdti6z.products_db (id,nombre,descripcion,precio,stock) VALUES(0,'"
                            + producto.getNombre() + "', '"
                            + producto.getDescripcion() + "', "
                            + producto.getPrecio() +", "
                            + producto.getStock()
                            + ")";
                    statement.executeUpdate(sql);
                    aux = true;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
            clean();
        }
        return aux;
    }

    public boolean consultaSiExisteNombre(String nombre) {
        boolean aux = false;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db WHERE " + "nombre" + " = " + "'" + nombre + "'";
                resultSet = statement.executeQuery(sql);
                aux = resultSet.next();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }
    public int buscaPorNombre(String nombre) {
        int aux = 0;
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db where nombre regexp '" + nombre + "'";
                resultSet = statement.executeQuery(sql);
                if (resultSet.next()) aux = resultSet.getInt("id");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }
}