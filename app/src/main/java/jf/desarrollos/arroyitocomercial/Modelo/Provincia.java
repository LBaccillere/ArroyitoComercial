package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;

import java.util.ArrayList;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.ProvinciaEntry;

public class  Provincia {

    private int id;
    private String nombre;
    private ArrayList<Ciudad> ciudades = new ArrayList<>();
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Provincia() {
    }

    public Provincia(int id, String nombre, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ProvinciaEntry.ID, id);
        values.put(ProvinciaEntry.NOMBRE, nombre);
        values.put(ProvinciaEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(ProvinciaEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(ProvinciaEntry.TIMESTAMP, timestamp);
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

    public void setSubrubro (Ciudad ciudad){
        ciudades.add(ciudad);
    }

    public ArrayList<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(ArrayList<Ciudad> ciudades) {
        this.ciudades = ciudades;
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
