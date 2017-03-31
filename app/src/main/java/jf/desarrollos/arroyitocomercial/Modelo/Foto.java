package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.FotoEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.FotoComercioEntry;

public class Foto {

    private int id;
    private String nombreArchivo;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Foto(int id, String nombreArchivo, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(FotoEntry.ID, id);
        values.put(FotoEntry.NOMBRE_ARCHIVO, nombreArchivo);
        values.put(FotoEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(FotoEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(FotoEntry.TIMESTAMP, timestamp);

        return values;
    }

    public ContentValues toContentValuesFotoComercio(int idFotoComercio, Comercio comercio) {
        ContentValues values = new ContentValues();

        values.put(FotoComercioEntry.ID, idFotoComercio);
        values.put(FotoComercioEntry.ID_FOTO, id);
        values.put(FotoComercioEntry.ID_COMERCIO, comercio.getId());
        values.put(FotoComercioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(FotoComercioEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(FotoComercioEntry.TIMESTAMP, timestamp);

        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
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
