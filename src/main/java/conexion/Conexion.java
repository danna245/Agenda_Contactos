package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    
    private static final String URL = "jdbc:mysql://localhost:3306/agenda_contactos"; 
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    /**
     * Establece y devuelve una conexión a la base de datos.
     * @return Un objeto Connection si la conexión es exitosa, o null si hay un error.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver"); 

            
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("🔌 Conexión establecida con la base de datos");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: No se encontró el controlador JDBC de MySQL. Asegúrate de que el JAR del conector MySQL esté en el Build Path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace(); 
        }
        return connection;
    }

    /**
     * Cierra una conexión a la base de datos.
     * @param connection La conexión a cerrar.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔌 Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}