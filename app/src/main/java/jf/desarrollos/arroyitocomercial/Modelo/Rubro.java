package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;

import java.util.ArrayList;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.RubroEntry;

public class  Rubro {

    private int id;
    private String nombre;
    private ArrayList<Subrubro> subrubros = new ArrayList<>();
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Rubro() {
    }

    public Rubro(int id, String nombre, String ultimaOperacion, String timestampModificacion, String timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(RubroEntry.ID, id);
        values.put(RubroEntry.NOMBRE, nombre);
        values.put(RubroEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(RubroEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(RubroEntry.TIMESTAMP, timestamp);
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

    public void setSubrubro (Subrubro subrubro){
        subrubros.add(subrubro);
    }

    public ArrayList<Subrubro> getSubrubros() {
        return subrubros;
    }

    public void setSubrubros(ArrayList<Subrubro> subrubros) {
        this.subrubros = subrubros;
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
