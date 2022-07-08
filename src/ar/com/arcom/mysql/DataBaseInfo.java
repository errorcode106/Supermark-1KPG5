package ar.com.arcom.mysql;

public class DataBaseInfo {
    private String JDBC_DRIVER;
    private String DB_URL;
    private String USER;
    private String PASSWORD;

// pruebas misteriosas e ilegales bsi5brxpk0wz9ygdti6z

    public DataBaseInfo() {
        //setDefaultDataBaseInfo();
        setCustomDataBaseInfo();
    }

    private void setDefaultDataBaseInfo(){
        JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        DB_URL = "jdbc:mysql://ulgg0or7rymoucea:KbmgO9lZCsLyLnKgWcGa@bsi5brxpk0wz9ygdti6z-mysql.services.clever-cloud.com:3306/bsi5brxpk0wz9ygdti6z";
        USER = "ulgg0or7rymoucea";
        PASSWORD = "KbmgO9lZCsLyLnKgWcGa";
    }

    private void setCustomDataBaseInfo(){
        JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        DB_URL = "jdbc:mysql://127.0.0.1";
        USER = "root";
        PASSWORD = "1234567890f";
    }

    /*private void registerJDBCDriver(){
    //PASO 2: Registrar JDBC driver
    try{
        Class.forName(JDBC_DRIVER);
    }catch(Exception e){
        // Resolver errores para Class.forName
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}*/
    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }
    public void setJDBC_DRIVER(String JDBC_DRIVER) {
        this.JDBC_DRIVER = JDBC_DRIVER;
    }
    public String getDB_URL() {
        return DB_URL;
    }
    public void setDB_URL(String DB_URL) {
        this.DB_URL = DB_URL;
    }
    public String getUSER() {
        return USER;
    }
    public void setUSER(String USER) {
        this.USER = USER;
    }
    public String getPASSWORD() {
        return PASSWORD;
    }
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
