package jf.desarrollos.arroyitocomercial.Modelo;

public class Evento {

    private int id;
    private String foto;
    private String titulo;
    private String descripcion;
    private double precio;
    private String sitioWeb;
    private Comercio comercio;
    private Direccion direccion;
    private String fechaInicio;
    private String fechaFin;
    private String horaInicio;
    private String horaFin;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Evento() {
    }

    public Evento(int id, String foto, String titulo, String descripcion, double precio, String sitioWeb, Comercio comercio, Direccion direccion, String fechaInicio, String fechaFin,
                  String horaInicio, String horaFin) {
        this.id = id;
        this.foto = foto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.sitioWeb = sitioWeb;
        this.comercio = comercio;
        this.direccion = direccion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String url) {
        this.sitioWeb = sitioWeb;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getUltimaOperacion() {
        return ultimaOperacion;
    }

    public void setUltimaOperacion(String ultimaOperacion) {
        this.ultimaOperacion = ultimaOperacion;
    }

    public String getTimestampModificacion() {
        return timestampModificacion;
    }

    public void setTimestampModificacion(String timestampModificacion) {
        this.timestampModificacion = timestampModificacion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
