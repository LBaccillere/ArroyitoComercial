package jf.desarrollos.arroyitocomercial.Modelo;

import android.content.ContentValues;

import java.util.ArrayList;

import jf.desarrollos.arroyitocomercial.Controlador.Busqueda;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.ComercioEntry;

public class Comercio {

    private int id;
    private ArrayList<Foto> fotos = new ArrayList<>();
    private String razonSocial;
    private String nombreFantasia;
    private Rubro rubro;
    private Subrubro subrubro;
    private ArrayList<Direccion> direcciones = new ArrayList<>();
    private ArrayList<Telefono> telefonos = new ArrayList<>();
    private ArrayList<Horario> horarios = new ArrayList<>();
    private ArrayList<Email> emails = new ArrayList<>();
    private ArrayList<SitioWeb> sitiosWebs = new ArrayList<>();
    private String descripcion;
    private String activo;
    private String ultimaOperacion;
    private String timestampModificacion;
    private String timestamp;

    public Comercio() {
    }

    public Comercio(int id, String razonSocial, String nombreFantasia, Rubro rubro, Subrubro subrubro, String descripcion, String activo, String ultimaOperacion, String timestampModificacion,
                    String timestamp) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.nombreFantasia = nombreFantasia;
        this.rubro = rubro;
        this.subrubro = subrubro;
        this.descripcion = descripcion;
        this.activo = activo;
        this.ultimaOperacion = ultimaOperacion;
        this.timestampModificacion = timestampModificacion;
        this.timestamp = timestamp;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ComercioEntry.ID, id);
        values.put(ComercioEntry.RAZON_SOCIAL, razonSocial);
        values.put(ComercioEntry.NOMBRE_FANTASIA, nombreFantasia);
        values.put(ComercioEntry.ID_RUBRO, rubro.getId());
        values.put(ComercioEntry.ID_SUBRUBRO, subrubro.getId());
        values.put(ComercioEntry.DESCRIPCION, descripcion);
        values.put(ComercioEntry.ACTIVO, activo);
        values.put(ComercioEntry.ULTIMA_OPERACION, ultimaOperacion);
        values.put(ComercioEntry.TIMESTAMP_MODIFICACION, timestampModificacion);
        values.put(ComercioEntry.TIMESTAMP, timestamp);

        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        this.fotos = fotos;
    }

    public void setFoto(Foto foto) {
        this.fotos.add(foto);
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public Subrubro getSubrubro() {
        return subrubro;
    }

    public void setSubrubro(Subrubro subrubro) {
        this.subrubro = subrubro;
    }

    public ArrayList<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(ArrayList<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public void setDireccion(Direccion direccion) {
        this.direcciones.add(direccion);
    }

    public ArrayList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public void setTelefono(Telefono telefono) {
        this.telefonos.add(telefono);
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }

    public void setHorario(Horario horario) {
        this.horarios.add(horario);
    }

    public ArrayList<Email> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<Email> emails) {
        this.emails = emails;
    }

    public void setEmail(Email email) {
        this.emails.add(email);
    }

    public ArrayList<SitioWeb> getSitiosWebs() {
        return sitiosWebs;
    }

    public void setSitiosWebs(ArrayList<SitioWeb> sitiosWebs) {
        this.sitiosWebs = sitiosWebs;
    }

    public void setSitioWeb(SitioWeb sitioWeb) {
        this.sitiosWebs.add(sitioWeb);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
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
