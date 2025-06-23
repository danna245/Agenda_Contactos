package modelo;

/**
 * Representa un número de teléfono asociado a un contacto.
 */
public class NumeroTelefono {
    private int idNumero;
    private int idContacto; 
    private String tipo;    
    private String numero;

    // Constructor vacío
    public NumeroTelefono() {
    }

    // Constructor con todos los campos
    public NumeroTelefono(int idNumero, int idContacto, String tipo, String numero) {
        this.idNumero = idNumero;
        this.idContacto = idContacto;
        this.tipo = tipo;
        this.numero = numero;
    }

    // Constructor para nuevas inserciones (sin idNumero)
    public NumeroTelefono(int idContacto, String tipo, String numero) {
        this.idContacto = idContacto;
        this.tipo = tipo;
        this.numero = numero;
    }

    // Getters y Setters
    public int getIdNumero() {
        return idNumero;
    }

    public void setIdNumero(int idNumero) {
        this.idNumero = idNumero;
    }

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return numero + " (" + tipo + ")"; 
    }
}