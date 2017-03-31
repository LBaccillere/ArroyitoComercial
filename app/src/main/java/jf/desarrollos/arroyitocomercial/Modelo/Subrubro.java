package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;
import android.database.Cursor;

import jf.desarrollos.arroyitocomercial.Modelo.Contract.SubrubroEntry;

public class Subrubro {

    private int id;
    private String nombre;
    private Rubro rubro;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Subrubro() {
    }

    public Subrubro(int id, String nombre, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(SubrubroEntry.ID, id);
        values.put(SubrubroEntry.NOMBRE, nombre);
        values.put(SubrubroEntry.ID_RUBRO, rubro.getId());
        values.put(SubrubroEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(SubrubroEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(SubrubroEntry.TIMESTAMP, timestamp);
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

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
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
