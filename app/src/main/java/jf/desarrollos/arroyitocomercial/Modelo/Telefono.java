package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.TelefonoEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.TelefonoComercioEntry;

public class Telefono {

    private int id;
    private String numero;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Telefono(int id, String numero, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.numero = numero;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(TelefonoEntry.ID, id);
        values.put(TelefonoEntry.NUMERO, numero);
        values.put(TelefonoEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(TelefonoEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(TelefonoEntry.TIMESTAMP, timestamp);

        return values;
    }

    public ContentValues toContentValuesTelefonoComercio(int idTelefonoComercio, Comercio comercio) {
        ContentValues values = new ContentValues();

        values.put(TelefonoComercioEntry.ID, idTelefonoComercio);
        values.put(TelefonoComercioEntry.ID_TELEFONO, id);
        values.put(TelefonoComercioEntry.ID_COMERCIO, comercio.getId());
        values.put(TelefonoComercioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(TelefonoComercioEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(TelefonoComercioEntry.TIMESTAMP, timestamp);

        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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
