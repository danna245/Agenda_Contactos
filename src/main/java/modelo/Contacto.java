package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Contacto {
    private int idContacto;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private boolean esFavorito;
    private String rutaFoto;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private List<NumeroTelefono> numerosTelefono; 

    /**
     * Constructor por defecto.
     */
    public Contacto() {
        this.numerosTelefono = new ArrayList<>();
    }

    /**
     * Constructor para cuando se recupera un contacto de la base de datos.
     * @param idContacto El ID único del contacto.
     * @param primerNombre El primer nombre del contacto.
     * @param segundoNombre El segundo nombre del contacto (opcional).
     * @param primerApellido El primer apellido del contacto.
     * @param segundoApellido El segundo apellido del contacto (opcional).
     * @param esFavorito Indica si el contacto es favorito.
     * @param rutaFoto La ruta al archivo de la foto del contacto (opcional).
     * @param fechaCreacion La fecha y hora de creación del registro.
     * @param fechaModificacion La fecha y hora de la última modificación del registro.
     */
    public Contacto(int idContacto, String primerNombre, String segundoNombre,
                    String primerApellido, String segundoApellido,
                    boolean esFavorito, String rutaFoto,
                    LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idContacto = idContacto;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.esFavorito = esFavorito;
        this.rutaFoto = rutaFoto;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.numerosTelefono = new ArrayList<>();
    }

    /**
     * Constructor para cuando se crea un nuevo contacto (sin idContacto, ni fechas de modificación).
     * @param primerNombre El primer nombre del contacto.
     * @param segundoNombre El segundo nombre del contacto (opcional).
     * @param primerApellido El primer apellido del contacto.
     * @param segundoApellido El segundo apellido del contacto (opcional).
     * @param esFavorito Indica si el contacto es favorito.
     * @param rutaFoto La ruta al archivo de la foto del contacto (opcional).
     */
    public Contacto(String primerNombre, String segundoNombre, String primerApellido,
                    String segundoApellido, boolean esFavorito, String rutaFoto) {
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.esFavorito = esFavorito;
        this.rutaFoto = rutaFoto;
        this.fechaCreacion = LocalDateTime.now();
        this.numerosTelefono = new ArrayList<>();
    }


    

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public boolean isEsFavorito() {
        return esFavorito;
    }

    public void setEsFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * Obtiene la lista de números de teléfono asociados a este contacto.
     * @return La lista de números de teléfono.
     */
    public List<NumeroTelefono> getNumerosTelefono() {
        return numerosTelefono;
    }

    /**
     * Establece la lista de números de teléfono asociados a este contacto.
     * @param numerosTelefono La lista de números de teléfono.
     */
    public void setNumerosTelefono(List<NumeroTelefono> numerosTelefono) {
        this.numerosTelefono = numerosTelefono;
    }

    /**
     * Añade un número de teléfono a la lista de números asociados a este contacto.
     * @param numero El objeto NumeroTelefono a añadir.
     */
    public void addNumeroTelefono(NumeroTelefono numero) {
        if (this.numerosTelefono == null) {
            this.numerosTelefono = new ArrayList<>();
        }
        this.numerosTelefono.add(numero);

    }

    @Override
    public String toString() {
        return "Contacto{" +
               "idContacto=" + idContacto +
               ", primerNombre='" + primerNombre + '\'' +
               ", segundoNombre='" + segundoNombre + '\'' +
               ", primerApellido='" + primerApellido + '\'' + 
               ", segundoApellido='" + segundoApellido + '\'' +
               ", esFavorito=" + esFavorito +
               ", rutaFoto='" + rutaFoto + '\'' + 
               ", fechaCreacion=" + fechaCreacion +
               ", fechaModificacion=" + fechaModificacion +
               ", numerosTelefono=" + numerosTelefono +
               '}';
    }
}