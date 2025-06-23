package dao;

import conexion.Conexion;
import modelo.HistorialLlamada;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialLlamadaDAO {

    /**
     * Inserta un nuevo registro de historial de llamada en la base de datos.
     * El idContacto debe estar ya establecido en el objeto HistorialLlamada.
     * @param historialLlamada El objeto HistorialLlamada a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insertarHistorialLlamada(HistorialLlamada historialLlamada) {
        String sql = "INSERT INTO historialllamadas (id_contacto, fecha_hora, duracion, tipo_llamada) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, historialLlamada.getIdContacto());
            pstmt.setTimestamp(2, Timestamp.valueOf(historialLlamada.getFechaHora()));
            pstmt.setString(3, historialLlamada.getDuracion());
            pstmt.setString(4, historialLlamada.getTipoLlamada());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        historialLlamada.setIdLlamada(generatedKeys.getInt(1));
                    }
                }
                System.out.println("✅ Registro de llamada para contacto ID " + historialLlamada.getIdContacto() + " insertado con éxito.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar historial de llamada: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Obtiene todos los registros del historial de llamadas asociados a un contacto específico.
     * @param idContacto El ID del contacto.
     * @return Una lista de objetos HistorialLlamada.
     */
    public List<HistorialLlamada> obtenerHistorialPorIdContacto(int idContacto) {
        List<HistorialLlamada> historial = new ArrayList<>();
        String sql = "SELECT id_llamada, id_contacto, fecha_hora, duracion, tipo_llamada FROM historialllamadas WHERE id_contacto = ? ORDER BY fecha_hora DESC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContacto);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    HistorialLlamada llamada = new HistorialLlamada();
                    llamada.setIdLlamada(rs.getInt("id_llamada"));
                    llamada.setIdContacto(rs.getInt("id_contacto"));
                    llamada.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                    llamada.setDuracion(rs.getString("duracion"));
                    llamada.setTipoLlamada(rs.getString("tipo_llamada"));
                    historial.add(llamada);
                }
                System.out.println("✅ Historial de llamadas obtenido con éxito para el contacto ID: " + idContacto);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener historial de llamadas por ID de contacto: " + e.getMessage());
            e.printStackTrace();
        }
        return historial;
    }

    /**
     * Actualiza un registro del historial de llamada existente en la base de datos.
     * @param historialLlamada El objeto HistorialLlamada con los datos actualizados (el idLlamada debe estar seteado).
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarHistorialLlamada(HistorialLlamada historialLlamada) {
        String sql = "UPDATE historialllamadas SET id_contacto = ?, fecha_hora = ?, duracion = ?, tipo_llamada = ? WHERE id_llamada = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, historialLlamada.getIdContacto());
            pstmt.setTimestamp(2, Timestamp.valueOf(historialLlamada.getFechaHora()));
            pstmt.setString(3, historialLlamada.getDuracion());
            pstmt.setString(4, historialLlamada.getTipoLlamada());
            pstmt.setInt(5, historialLlamada.getIdLlamada());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Registro de llamada ID: " + historialLlamada.getIdLlamada() + " actualizado con éxito.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar historial de llamada: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina un registro del historial de llamada de la base de datos por su ID.
     * @param idLlamada El ID del registro a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarHistorialLlamada(int idLlamada) {
        String sql = "DELETE FROM historialllamadas WHERE id_llamada = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idLlamada);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Registro de llamada ID: " + idLlamada + " eliminado con éxito.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar historial de llamada: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina todos los registros de historial de llamadas asociados a un contacto específico.
     * Esto es útil si se elimina un contacto y se desea borrar su historial.
     * @param idContacto El ID del contacto cuyos registros se desean eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarHistorialPorIdContacto(int idContacto) {
        String sql = "DELETE FROM historialllamadas WHERE id_contacto = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContacto);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Todos los registros de llamadas para el contacto ID: " + idContacto + " eliminados con éxito.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar historial de llamadas por ID de contacto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}