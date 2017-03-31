package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.HorarioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.HorarioComercioEntry;

import java.sql.Time;

public class Horario {

    private int id;
    private String dia;
    private Time aperturaMañana;
    private Time aperturaTarde;
    private Time cierreMañana;
    private Time cierreTarde;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Horario() {
    }

    public Horario(int id, String dia, Time aperturaMañana, Time cierreMañana, Time aperturaTarde, Time cierreTarde,
                   String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.dia = dia;
        this.aperturaMañana = aperturaMañana;
        this.cierreMañana = cierreMañana;
        this.aperturaTarde = aperturaTarde;
        this.cierreTarde = cierreTarde;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(HorarioEntry.ID, id);
        values.put(HorarioEntry.DIA, dia);
        values.put(HorarioEntry.APERTURA_MANIANA, String.valueOf(aperturaMañana));
        values.put(HorarioEntry.CIERRE_MANIANA, String.valueOf(cierreMañana));
        values.put(HorarioEntry.APERTURA_TARDE, String.valueOf(aperturaTarde));
        values.put(HorarioEntry.CIERRE_TARDE, String.valueOf(cierreTarde));
        values.put(HorarioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(HorarioEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(HorarioEntry.TIMESTAMP, timestamp);

        return values;
    }

    public ContentValues toContentValuesHorarioComercio(int idHorarioComercio, Comercio comercio) {
        ContentValues values = new ContentValues();

        values.put(HorarioComercioEntry.ID, idHorarioComercio);
        values.put(HorarioComercioEntry.ID_HORARIO, id);
        values.put(HorarioComercioEntry.ID_COMERCIO, comercio.getId());
        values.put(HorarioComercioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(HorarioComercioEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(HorarioComercioEntry.TIMESTAMP, timestamp);

        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Time getAperturaMañana() {
        return aperturaMañana;
    }

    public void setAperturaMañana(Time aperturaMañana) {
        this.aperturaMañana = aperturaMañana;
    }

    public Time getAperturaTarde() {
        return aperturaTarde;
    }

    public void setAperturaTarde(Time aperturaTarde) {
        this.aperturaTarde = aperturaTarde;
    }

    public Time getCierreMañana() {
        return cierreMañana;
    }

    public void setCierreMañana(Time cierreMañana) {
        this.cierreMañana = cierreMañana;
    }

    public Time getCierreTarde() {
        return cierreTarde;
    }

    public void setCierreTarde(Time cierreTarde) {
        this.cierreTarde = cierreTarde;
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
