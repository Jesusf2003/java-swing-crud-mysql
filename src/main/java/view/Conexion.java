package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String user = "root";
    private static final String pswd = "rootpassword";
    private static Connection cn;

    private Conexion() {}

    public static Connection getConnection() {
        if (cn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                cn = DriverManager.getConnection(URL, user, pswd);
                System.out.println("Conexión exitosa a la base de datos");
            } catch (ClassNotFoundException e) {
                System.err.println("Error: No se pudo cargar el driver JDBC.");
            } catch (SQLException e) {
                System.err.println("Error al conectar la base de datos");
                e.printStackTrace();
            }
        }
        return cn;
    }

    public static void close() {
        if (cn != null) {
            try {
                cn.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la base de datos");
                e.printStackTrace();
            }
        }
    }
}
