package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SincronizacionEntry;

public class Sincronizacion {

    private int id;
    private String clase;
    private String timestampModificacionDatos;
    private String timestampModificacion;
    private String timestamp;

    public Sincronizacion() {
    }

    public Sincronizacion(String timestamp, String timestampModificacion, String timestampModificacionDatos, String clase) {
        this.timestamp = timestamp;
        this.timestampModificacion = timestampModificacion;
        this.timestampModificacionDatos = timestampModificacionDatos;
        this.clase = clase;
    }

    public Sincronizacion(String clase, String timestampModificacion, String timestamp) {
        this.clase = clase;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(SincronizacionEntry.CLASE, clase);
        values.put(SincronizacionEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(SincronizacionEntry.TIMESTAMP, timestamp);
        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getTimestampModificacionDatos() {
        return timestampModificacionDatos;
    }

    public void setTimestampModificacionDatos(String timestampModificacionDatos) {
        this.timestampModificacionDatos = timestampModificacionDatos;
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
