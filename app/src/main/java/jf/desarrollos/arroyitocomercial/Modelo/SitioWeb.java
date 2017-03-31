package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SitioWebEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SitioWebComercioEntry;

public class SitioWeb {

    private int id;
    private String url;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public SitioWeb(int id, String url, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.url = url;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(SitioWebEntry.ID, id);
        values.put(SitioWebEntry.URL, url);
        values.put(SitioWebEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(SitioWebEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(SitioWebEntry.TIMESTAMP, timestamp);

        return values;
    }

    public ContentValues toContentValuesSitioWebComercio(int idSitioWebComercio, Comercio comercio) {
        ContentValues values = new ContentValues();

        values.put(SitioWebComercioEntry.ID, idSitioWebComercio);
        values.put(SitioWebComercioEntry.ID_SITIO_WEB, id);
        values.put(SitioWebComercioEntry.ID_COMERCIO, comercio.getId());
        values.put(SitioWebComercioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(SitioWebComercioEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(SitioWebComercioEntry.TIMESTAMP, timestamp);

        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
