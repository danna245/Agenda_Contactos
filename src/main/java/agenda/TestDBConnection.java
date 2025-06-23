package agenda;

import conexion.Conexion; 

public class TestDBConnection { 
    public static void main(String[] args) {
        System.out.println("Intentando probar la conexión a la base de datos...");
        if (Conexion.getConnection() != null) { 
            System.out.println("🎉 ¡Conexión a la base de datos exitosa!");
        } else {
            System.err.println("❌ ¡Fallo la conexión a la base de datos!");
            System.err.println("Por favor, revisa:");
            System.err.println("  - Que MySQL esté corriendo.");
            System.err.println("  - Las credenciales (usuario/contraseña) en DatabaseConnection.java.");
            System.err.println("  - El nombre de la base de datos en DatabaseConnection.java.");
            System.err.println("  - Que el JAR del conector MySQL esté en la carpeta 'lib'.");
            System.err.println("  - Que la base de datos 'agenda_contactos' y sus tablas existan en MySQL.");
        }
    }
}