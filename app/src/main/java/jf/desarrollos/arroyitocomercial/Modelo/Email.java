package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.EmailEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.EmailComercioEntry;

public class Email {

    private int id;
    private String direccion;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Email(int id, String direccion, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.direccion = direccion;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(EmailEntry.ID, id);
        values.put(EmailEntry.DIRECCION, direccion);
        values.put(Contract.DireccionEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(EmailEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(EmailEntry.TIMESTAMP, timestamp);

        return values;
    }

    public ContentValues toContentValuesEmailComercio(int idEmailComercio, Comercio comercio) {
        ContentValues values = new ContentValues();

        values.put(EmailComercioEntry.ID, idEmailComercio);
        values.put(EmailComercioEntry.ID_EMAIL, id);
        values.put(EmailComercioEntry.ID_COMERCIO, comercio.getId());
        values.put(EmailComercioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(EmailComercioEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(EmailComercioEntry.TIMESTAMP, timestamp);

        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
