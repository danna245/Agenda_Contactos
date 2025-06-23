package dao;

import modelo.Contacto;
import conexion.Conexion; // Importa desde el paquete conexion
import modelo.NumeroTelefono; // Necesario si vamos a cargar números también
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos (DAO) para la entidad Contacto.
 * Proporciona métodos para interactuar con la tabla 'contactos' en la base de datos.
 */
public class ContactoDAO {

    private NumeroTelefonoDAO numeroTelefonoDAO;
    private HistorialLlamadaDAO historialLlamadaDAO;

    public ContactoDAO() {
        this.numeroTelefonoDAO = new NumeroTelefonoDAO();
        this.historialLlamadaDAO = new HistorialLlamadaDAO();
    }

    /**
     * Inserta un nuevo contacto en la base de datos.
     *
     * @param contacto El objeto Contacto a insertar. El idContacto se actualizará con el ID generado.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insertarContacto(Contacto contacto) {
        String sql = "INSERT INTO contactos (primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, es_favorito, ruta_foto, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection(); // Usar Conexion
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, contacto.getPrimerNombre());
            pstmt.setString(2, contacto.getSegundoNombre());
            pstmt.setString(3, contacto.getPrimerApellido());
            pstmt.setString(4, contacto.getSegundoApellido());
            pstmt.setBoolean(5, contacto.isEsFavorito());
            pstmt.setString(6, contacto.getRutaFoto());
            pstmt.setTimestamp(7, Timestamp.valueOf(contacto.getFechaCreacion() != null ? contacto.getFechaCreacion() : LocalDateTime.now())); // Guardar fecha de creación

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contacto.setIdContacto(generatedKeys.getInt(1));
                        System.out.println("✅ Contacto insertado con ID: " + contacto.getIdContacto());
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar contacto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Actualiza un contacto existente en la base de datos.
     *
     * @param contacto El objeto Contacto con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarContacto(Contacto contacto) {
        String sql = "UPDATE contactos SET primer_nombre = ?, segundo_nombre = ?, primer_apellido = ?, " +
                     "segundo_apellido = ?, es_favorito = ?, ruta_foto = ?, fecha_modificacion = ? WHERE id_contacto = ?";
        try (Connection conn = Conexion.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contacto.getPrimerNombre());
            pstmt.setString(2, contacto.getSegundoNombre());
            pstmt.setString(3, contacto.getPrimerApellido());
            pstmt.setString(4, contacto.getSegundoApellido());
            pstmt.setBoolean(5, contacto.isEsFavorito());
            pstmt.setString(6, contacto.getRutaFoto());
            pstmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(8, contacto.getIdContacto());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Contacto ID: " + contacto.getIdContacto() + " actualizado con éxito.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar contacto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Obtiene un contacto por su ID de la base de datos.
     * Además, carga los números de teléfono asociados a ese contacto.
     *
     * @param idContacto El ID del contacto a buscar.
     * @return El objeto Contacto si se encuentra, o null si no existe.
     */
    public Contacto obtenerContactoPorId(int idContacto) {
        Contacto contacto = null;
        String sql = "SELECT id_contacto, primer_nombre, segundo_nombre, primer_apellido, " +
                     "segundo_apellido, es_favorito, ruta_foto, fecha_creacion, fecha_modificacion " +
                     "FROM contactos WHERE id_contacto = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContacto);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contacto = new Contacto(
                        rs.getInt("id_contacto"),
                        rs.getString("primer_nombre"),
                        rs.getString("segundo_nombre"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido"),
                        rs.getBoolean("es_favorito"),
                        rs.getString("ruta_foto"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getTimestamp("fecha_modificacion") != null ? rs.getTimestamp("fecha_modificacion").toLocalDateTime() : null
                    );
                    
                    List<NumeroTelefono> numeros = numeroTelefonoDAO.obtenerNumerosPorIdContacto(idContacto);
                    contacto.setNumerosTelefono(numeros);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener contacto por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return contacto;
    }

    /**
     * Elimina un contacto de la base de datos.
     * Esto también eliminará sus números de teléfono y el historial de llamadas asociados.
     *
     * @param idContacto El ID del contacto a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarContacto(int idContacto) {
        
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false); 

            
            if (historialLlamadaDAO.eliminarHistorialPorIdContacto(idContacto)) {
                System.out.println("✅ Historial de llamadas para contacto ID: " + idContacto + " eliminado.");
            } else {
                System.out.println("⚠️ No se encontró historial de llamadas para el contacto ID: " + idContacto + " o hubo un error al eliminar.");
            }

            
            if (numeroTelefonoDAO.eliminarNumerosPorIdContacto(idContacto)) {
                System.out.println("✅ Números de teléfono para contacto ID: " + idContacto + " eliminados.");
            } else {
                System.out.println("⚠️ No se encontraron números de teléfono para el contacto ID: " + idContacto + " o hubo un error al eliminar.");
            }

           
            String sql = "DELETE FROM contactos WHERE id_contacto = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idContacto);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    conn.commit(); 
                    System.out.println("✅ Contacto ID: " + idContacto + " eliminado con éxito.");
                    return true;
                } else {
                    conn.rollback(); 
                    System.err.println("❌ No se encontró el contacto ID: " + idContacto + " para eliminar.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar contacto: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); 
                    System.err.println("🔄 Transacción revertida debido a un error.");
                } catch (SQLException ex) {
                    System.err.println("❌ Error al revertir la transacción: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); 
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("❌ Error al cerrar conexión en eliminarContacto: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Obtiene todos los contactos de la base de datos.
     *
     * @return Una lista de objetos Contacto.
     */
    public List<Contacto> obtenerTodosLosContactos() {
        List<Contacto> contactos = new ArrayList<>();
        String sql = "SELECT id_contacto, primer_nombre, segundo_nombre, primer_apellido, " +
                     "segundo_apellido, es_favorito, ruta_foto, fecha_creacion, fecha_modificacion " +
                     "FROM contactos ORDER BY primer_nombre, primer_apellido";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contacto contacto = new Contacto(
                    rs.getInt("id_contacto"),
                    rs.getString("primer_nombre"),
                    rs.getString("segundo_nombre"),
                    rs.getString("primer_apellido"),
                    rs.getString("segundo_apellido"),
                    rs.getBoolean("es_favorito"),
                    rs.getString("ruta_foto"),
                    rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                    rs.getTimestamp("fecha_modificacion") != null ? rs.getTimestamp("fecha_modificacion").toLocalDateTime() : null
                );
                
                contactos.add(contacto);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los contactos: " + e.getMessage());
            e.printStackTrace();
        }
        return contactos;
    }

    /**
     * Obtiene solo los contactos marcados como favoritos de la base de datos.
     *
     * @return Una lista de objetos Contacto marcados como favoritos.
     */
    public List<Contacto> obtenerContactosFavoritos() {
        List<Contacto> contactosFavoritos = new ArrayList<>();
        String sql = "SELECT id_contacto, primer_nombre, segundo_nombre, primer_apellido, " +
                     "segundo_apellido, es_favorito, ruta_foto, fecha_creacion, fecha_modificacion " +
                     "FROM contactos WHERE es_favorito = TRUE ORDER BY primer_nombre, primer_apellido";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Contacto contacto = new Contacto(
                    rs.getInt("id_contacto"),
                    rs.getString("primer_nombre"),
                    rs.getString("segundo_nombre"),
                    rs.getString("primer_apellido"),
                    rs.getString("segundo_apellido"),
                    rs.getBoolean("es_favorito"),
                    rs.getString("ruta_foto"),
                    rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                    rs.getTimestamp("fecha_modificacion") != null ? rs.getTimestamp("fecha_modificacion").toLocalDateTime() : null
                );
                contactosFavoritos.add(contacto);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener contactos favoritos: " + e.getMessage());
            e.printStackTrace();
        }
        return contactosFavoritos;
    }

    /**
     * Busca contactos en la base de datos que coincidan con un criterio
     * en su primer nombre, segundo nombre, primer apellido o segundo apellido.
     * La búsqueda no es sensible a mayúsculas/minúsculas y busca coincidencias parciales.
     *
     * @param criterio El texto a buscar en los nombres y apellidos de los contactos.
     * @return Una lista de objetos Contacto que coinciden con el criterio de búsqueda.
     * Si no hay coincidencias, devuelve una lista vacía.
     */
    public List<Contacto> buscarContactos(String criterio) {
        List<Contacto> contactos = new ArrayList<>();
        
        // Se usa LOWER() para hacer la búsqueda insensible a mayúsculas/minúsculas
        String sql = "SELECT id_contacto, primer_nombre, segundo_nombre, primer_apellido, " +
                     "segundo_apellido, es_favorito, ruta_foto, fecha_creacion, fecha_modificacion " +
                     "FROM contactos WHERE " +
                     "LOWER(primer_nombre) LIKE LOWER(?) OR " +
                     "LOWER(segundo_nombre) LIKE LOWER(?) OR " +
                     "LOWER(primer_apellido) LIKE LOWER(?) OR " +
                     "LOWER(segundo_apellido) LIKE LOWER(?) " +
                     "ORDER BY primer_nombre, primer_apellido";

        try (Connection conn = Conexion.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            
            String searchPattern = "%" + criterio + "%";

            
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Contacto contacto = new Contacto(
                        rs.getInt("id_contacto"),
                        rs.getString("primer_nombre"),
                        rs.getString("segundo_nombre"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido"),
                        rs.getBoolean("es_favorito"),
                        rs.getString("ruta_foto"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getTimestamp("fecha_modificacion") != null ? rs.getTimestamp("fecha_modificacion").toLocalDateTime() : null
                    );
                    contactos.add(contacto);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al buscar contactos: " + e.getMessage());
            e.printStackTrace(); 
        }
        return contactos;
    }
}