package jf.desarrollos.arroyitocomercial.Modelo;

public class Notificacion {

    private int id;
    private String titulo;
    private String mensaje;
    private String fechaEnvio;
    private String horaEnvio;
    private String fechaFin;
    private Comercio comercio;

    public Notificacion() {
    }

    public Notificacion(int id, String titulo, String mensaje, String fechaEnvio, String horaEnvio, String fechaFin, Comercio comercio) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.horaEnvio = horaEnvio;
        this.fechaFin = fechaFin;
        this.comercio = comercio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getHoraEnvio() {
        return horaEnvio;
    }

    public void setHoraEnvio(String horaEnvio) {
        this.horaEnvio = horaEnvio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }
}
