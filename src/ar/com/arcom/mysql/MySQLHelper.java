package ar.com.arcom.mysql;

import ar.com.arcom.bin.Articulo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
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
        } //cierra finally try
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
    public int añadeUsuario(String user, String password) {
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
                    aux = extractData("type",dataType);
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
    private String extractData(String label,String dataType){
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

    public List<List<String>> obtenerProductos() {
        return dameProducto();
    }
    private List<List<String>> dameProducto(){
        List<List<String>> aux = new ArrayList<>();
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) {
                    aux = extractData();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        clean();
        return aux;
    }

    private List<List<String>> extractData() {
        //PASO 3: Extraer datos de un ResulSet
        List<List<String>> valor = new ArrayList<>();
        try {
            do{
                List<String> aux = new ArrayList<>();
                aux.add(resultSet.getString("id"));
                aux.add(resultSet.getString("nombre"));
                aux.add(resultSet.getString("descripcion"));
                aux.add(resultSet.getString("precio"));
                aux.add(resultSet.getString("stock"));
                valor.add(aux);
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
            articulos.add(dameArticulo(lista.get(i).getId()));
            articulos.get(articulos.size()-1).add(String.valueOf(lista.get(i).getCantidad()));
        }
        return articulos;
    }
    private List<String> dameArticulo(int id) {
        List<String> aux = new ArrayList<>();
        openConection();
        if (openConnection){
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM bsi5brxpk0wz9ygdti6z.products_db WHERE id = " + "'" + id + "'";
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) {
                    aux.add(resultSet.getString("id"));
                    aux.add(resultSet.getString("nombre"));
                    aux.add(resultSet.getString("descripcion"));
                    aux.add(resultSet.getString("precio"));
                }
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
}