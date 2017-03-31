package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.DireccionEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.DireccionComercioEntry;

public class Direccion {
    private int id;
    private String nombre;
    private int altura;
    private int piso;
    private String departamento;
    private String barrio;
    private Ciudad ciudad;
    private Provincia provincia;
    private String tipo;
    private String latitud;
    private String longitud;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Direccion() {
    }

    public Direccion(int id, String nombre, int altura, int piso, String departamento, String barrio, Ciudad ciudad, Provincia provincia, String tipo,
                     String latitud, String longitud, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.altura = altura;
        this.piso = piso;
        this.departamento = departamento;
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.tipo = tipo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(DireccionEntry.ID, id);
        values.put(DireccionEntry.NOMBRE, nombre);
        values.put(DireccionEntry.ALTURA, altura);
        values.put(DireccionEntry.NUMERO_PISO, piso);
        values.put(DireccionEntry.DEPARTAMENTO, departamento);
        values.put(DireccionEntry.BARRIO, barrio);
        values.put(DireccionEntry.ID_CIUDAD, ciudad.getId());
        values.put(DireccionEntry.ID_PROVINCIA, provincia.getId());
        values.put(DireccionEntry.TIPO, tipo);
        values.put(DireccionEntry.LATITUD, latitud);
        values.put(DireccionEntry.LONGITUD, longitud);
        values.put(DireccionEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(DireccionEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(DireccionEntry.TIMESTAMP, timestamp);

        return values;
    }

    public ContentValues toContentValuesDireccionComercio(int idDireccionComercio, Comercio comercio) {
        ContentValues values = new ContentValues();

        values.put(DireccionComercioEntry.ID, idDireccionComercio);
        values.put(DireccionComercioEntry.ID_DIRECCION, id);
        values.put(DireccionComercioEntry.ID_COMERCIO, comercio.getId());
        values.put(DireccionComercioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(DireccionComercioEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(DireccionComercioEntry.TIMESTAMP, timestamp);

        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
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
