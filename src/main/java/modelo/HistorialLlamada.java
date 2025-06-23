package modelo;

import java.time.LocalDateTime;

/**
 * Representa un registro en el historial de llamadas.
 */
public class HistorialLlamada {
    private int idLlamada;
    private int idContacto; 
    private LocalDateTime fechaHora;
    private String duracion; 
    private String tipoLlamada; 

    
    public HistorialLlamada() {
    }

    
    public HistorialLlamada(int idLlamada, int idContacto, LocalDateTime fechaHora, String duracion, String tipoLlamada) {
        this.idLlamada = idLlamada;
        this.idContacto = idContacto;
        this.fechaHora = fechaHora;
        this.duracion = duracion;
        this.tipoLlamada = tipoLlamada;
    }

    // Constructor para nuevas llamadas (sin idLlamada, se genera automáticamente)
    public HistorialLlamada(int idContacto, LocalDateTime fechaHora, String duracion, String tipoLlamada) {
        this.idContacto = idContacto;
        this.fechaHora = fechaHora;
        this.duracion = duracion;
        this.tipoLlamada = tipoLlamada;
    }

    // Getters y Setters
    public int getIdLlamada() {
        return idLlamada;
    }

    public void setIdLlamada(int idLlamada) {
        this.idLlamada = idLlamada;
    }

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getTipoLlamada() {
        return tipoLlamada;
    }

    public void setTipoLlamada(String tipoLlamada) {
        this.tipoLlamada = tipoLlamada;
    }

    @Override
    public String toString() {
        return "HistorialLlamada{" +
               "idLlamada=" + idLlamada +
               ", idContacto=" + idContacto +
               ", fechaHora=" + fechaHora +
               ", duracion='" + duracion + '\'' +
               ", tipoLlamada='" + tipoLlamada + '\'' +
               '}';
    }
}