package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;

import jf.desarrollos.arroyitocomercial.Modelo.Contract.CiudadEntry;

public class Ciudad {

    private int id;
    private String nombre;
    private Provincia provincia;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Ciudad() {
    }

    public Ciudad(int id, String nombre, Provincia provincia, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(CiudadEntry.ID, id);
        values.put(CiudadEntry.NOMBRE, nombre);
        values.put(CiudadEntry.ID_PROVINCIA, provincia.getId());
        values.put(Contract.ComercioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(CiudadEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(CiudadEntry.TIMESTAMP, timestamp);
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
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
