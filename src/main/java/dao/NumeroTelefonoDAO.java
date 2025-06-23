package dao;

import conexion.Conexion;
import modelo.NumeroTelefono;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NumeroTelefonoDAO {

    /**
     * Inserta un nuevo número de teléfono en la base de datos.
     * El idContacto debe estar ya establecido en el objeto NumeroTelefono.
     * @param numeroTelefono El objeto NumeroTelefono a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insertarNumeroTelefono(NumeroTelefono numeroTelefono) {
        String sql = "INSERT INTO numerostelefono (id_contacto, tipo_numero, numero) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, numeroTelefono.getIdContacto());
            pstmt.setString(2, numeroTelefono.getTipo());
            pstmt.setString(3, numeroTelefono.getNumero());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        numeroTelefono.setIdNumero(generatedKeys.getInt(1));
                    }
                }
                System.out.println("✅ Número de teléfono '" + numeroTelefono.getNumero() + "' insertado con éxito para el contacto ID: " + numeroTelefono.getIdContacto());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar número de teléfono: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Obtiene todos los números de teléfono asociados a un contacto específico.
     * @param idContacto El ID del contacto.
     * @return Una lista de objetos NumeroTelefono.
     */
    public List<NumeroTelefono> obtenerNumerosPorIdContacto(int idContacto) {
        List<NumeroTelefono> numeros = new ArrayList<>();
        String sql = "SELECT id_numero, id_contacto, tipo_numero, numero FROM numerostelefono WHERE id_contacto = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContacto);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NumeroTelefono numero = new NumeroTelefono();
                    numero.setIdNumero(rs.getInt("id_numero"));
                    numero.setIdContacto(rs.getInt("id_contacto"));
                    numero.setTipo(rs.getString("tipo_numero"));
                    numero.setNumero(rs.getString("numero"));
                    numeros.add(numero);
                }
                System.out.println("✅ Números de teléfono obtenidos con éxito para el contacto ID: " + idContacto);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener números de teléfono por ID de contacto: " + e.getMessage());
            e.printStackTrace();
        }
        return numeros;
    }

    /**
     * Actualiza un número de teléfono existente en la base de datos.
     * @param numeroTelefono El objeto NumeroTelefono con los datos actualizados (el idNumero debe estar seteado).
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarNumeroTelefono(NumeroTelefono numeroTelefono) {
        String sql = "UPDATE numerostelefono SET id_contacto = ?, tipo_numero = ?, numero = ? WHERE id_numero = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numeroTelefono.getIdContacto());
            pstmt.setString(2, numeroTelefono.getTipo());
            pstmt.setString(3, numeroTelefono.getNumero());
            pstmt.setInt(4, numeroTelefono.getIdNumero());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Número de teléfono ID: " + numeroTelefono.getIdNumero() + " actualizado con éxito.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar número de teléfono: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina un número de teléfono de la base de datos por su ID.
     * @param idNumero El ID del número de teléfono a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarNumeroTelefono(int idNumero) {
        String sql = "DELETE FROM numerostelefono WHERE id_numero = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idNumero);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Número de teléfono ID: " + idNumero + " eliminado con éxito.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar número de teléfono: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina todos los números de teléfono asociados a un contacto específico.
     * @param idContacto El ID del contacto cuyos números se desean eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarNumerosPorIdContacto(int idContacto) {
        String sql = "DELETE FROM numerostelefono WHERE id_contacto = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContacto);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Todos los números de teléfono para el contacto ID: " + idContacto + " eliminados con éxito.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar números de teléfono por ID de contacto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}